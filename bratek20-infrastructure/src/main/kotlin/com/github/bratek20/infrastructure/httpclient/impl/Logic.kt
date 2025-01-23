package com.github.bratek20.infrastructure.httpclient.impl

import com.github.bratek20.architecture.serialization.api.SerializationType
import com.github.bratek20.architecture.serialization.api.SerializedValue
import com.github.bratek20.architecture.serialization.api.Serializer
import com.github.bratek20.architecture.structs.api.Struct
import com.github.bratek20.architecture.serialization.context.SerializationFactory
import com.github.bratek20.infrastructure.httpclient.api.*
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.util.*

class HttpClientFactoryLogic(
    private val requester: HttpRequester
) : HttpClientFactory {
    override fun create(config: HttpClientConfig): HttpClient {
        return HttpClientLogic(requester, config)
    }
}

class HttpRequesterLogic : HttpRequester {
    private val restTemplate: RestTemplate = RestTemplate()

    override fun send(request: HttpRequest): SendResponse {
        val entity = HttpEntity<String>(request.getContent(), HttpHeaders().apply {
            request.getHeaders().forEach { h -> this[h.getKey()] = h.getValue() }
        })

        val responseEntity: ResponseEntity<String> = restTemplate.exchange(
            request.getUrl(),
            mapMethod(request.getMethod()),
            entity,
            String::class.java
        )


        val headers = responseEntity.headers.map { HttpHeader.create(it.key, it.value.joinToString()) }
        return SendResponse.create(
            responseEntity.statusCode.value(),
            responseEntity.body!!,
            headers
        )
    }

    private fun mapMethod(method: com.github.bratek20.infrastructure.httpclient.api.HttpMethod): HttpMethod {
        return when (method) {
            com.github.bratek20.infrastructure.httpclient.api.HttpMethod.GET -> HttpMethod.GET
            com.github.bratek20.infrastructure.httpclient.api.HttpMethod.POST -> HttpMethod.POST
        }
    }
}

class HttpClientLogic(
    private val requester: HttpRequester,
    private val config: HttpClientConfig
) : HttpClient {


    override fun get(path: String): HttpResponse {
        val sendResponse = requester.send(
            HttpRequest.create(
                getFullUrl(path),
                com.github.bratek20.infrastructure.httpclient.api.HttpMethod.GET,
                null,
                "application/json",
                getHeaders()
            )
        )

        return HttpResponseLogic(sendResponse)
    }

    override fun post(path: String, body: Any?): HttpResponse {
        val sendResponse = requester.send(
            HttpRequest.create(
                getFullUrl(path),
                com.github.bratek20.infrastructure.httpclient.api.HttpMethod.POST,
                body?.let { SERIALIZER.serialize(it).getValue() },
                "application/json",
                getHeaders()
            )
        )

        extractPassedException(sendResponse)?.let {
            val type = Class.forName(it.`package` + "." + it.type)
            throw type.getConstructor(String::class.java).newInstance(it.message) as Throwable
        }

        return HttpResponseLogic(sendResponse)
    }

    private fun getHeaders(): List<HttpHeader> {
        return config.getAuth()?.let {
            val auth = "${it.getUsername()}:${it.getPassword()}"
            val encodedAuth = Base64.getEncoder().encodeToString(auth.toByteArray())
            listOf(HttpHeader.create("Authorization", "Basic $encodedAuth"))
        } ?: emptyList()
    }

    data class PassedException(
        val type: String,
        val `package`: String,
        val message: String
    )

    data class PassedExceptionResponse(
        val passedException: PassedException
    )

    private fun extractPassedException(sendResponse: SendResponse): PassedException? {
        return try {
            SERIALIZER.deserialize(
                SerializedValue.create(sendResponse.getBody(), SerializationType.JSON),
                PassedExceptionResponse::class.java
            ).passedException
        } catch (e: Exception) {
            null
        }
    }

    private fun getFullUrl(path: String): String {
        return UriComponentsBuilder.fromHttpUrl(config.getBaseUrl()).path(path).toUriString()
    }

    companion object {
        private val SERIALIZER: Serializer = SerializationFactory.createSerializer()
    }
}

class HttpResponseLogic(
    private val sendResponse: SendResponse
) : HttpResponse {
    override fun getStatusCode(): Int {
        return sendResponse.getStatusCode()
    }

    override fun <T> getBody(clazz: Class<T>): T {
        return sendResponse.getBody().let {
            SERIALIZER.deserialize(
                SerializedValue.create(it, SerializationType.JSON),
                clazz
            )
        }
    }

    companion object {
        private val SERIALIZER: Serializer = SerializationFactory.createSerializer()
    }
}

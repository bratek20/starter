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

class HttpClientFactoryLogic : HttpClientFactory {
    override fun create(config: HttpClientConfig): HttpClient {
        return HttpClientLogic(config)
    }
}

class HttpClientLogic(
    private val config: HttpClientConfig
) : HttpClient {
    private val restTemplate: RestTemplate = RestTemplate()

    override fun get(path: String): HttpResponse {
        val headers = HttpHeaders().apply {
            addAuthHeaderIfPresent(this)
        }
        val entity = HttpEntity<String>(headers)
        val responseEntity: ResponseEntity<String> = restTemplate.exchange(
            getFullUrl(path),
            HttpMethod.GET,
            entity,
            String::class.java
        )

        return HttpResponseLogic(responseEntity.statusCode.value(), responseEntity.body)
    }

    override fun post(path: String, body: Any?): HttpResponse {
        val headers = HttpHeaders().apply {
            addAuthHeaderIfPresent(this)
        }
        val entity = HttpEntity(body?.let { getHttpBody(body) }, headers)
        val responseEntity: ResponseEntity<String> = restTemplate.exchange(
            getFullUrl(path),
            HttpMethod.POST,
            entity,
            String::class.java
        )

        extractPassedException(responseEntity)?.let {
            val type = Class.forName(it.`package` + "." + it.type)
            throw type.getConstructor(String::class.java).newInstance(it.message) as Throwable
        }

        return HttpResponseLogic(responseEntity.statusCode.value(), responseEntity.body)
    }

    private fun addAuthHeaderIfPresent(headers: HttpHeaders) {
        config.getAuth()?.let {
            val auth = "${it.getUsername()}:${it.getPassword()}"
            val encodedAuth = Base64.getEncoder().encodeToString(auth.toByteArray())
            headers["Authorization"] = "Basic $encodedAuth"
        }
    }

    data class PassedException(
        val type: String,
        val `package`: String,
        val message: String
    )

    data class PassedExceptionResponse(
        val passedException: PassedException
    )

    private fun extractPassedException(responseEntity: ResponseEntity<String>): PassedException? {
        return try {
            SERIALIZER.deserialize(
                SerializedValue.create(responseEntity.body!!, SerializationType.JSON),
                PassedExceptionResponse::class.java
            ).passedException
        } catch (e: Exception) {
            null
        }
    }

    private fun getHttpBody(body: Any): Struct {
        return SERIALIZER.asStruct(body)
    }

    private fun getFullUrl(path: String): String {
        return UriComponentsBuilder.fromHttpUrl(config.getBaseUrl()).path(path).toUriString()
    }

    companion object {
        private val SERIALIZER: Serializer = SerializationFactory.createSerializer()
    }
}

class HttpResponseLogic(
    private val statusCode: Int,
    private val body: String?,
) : HttpResponse {
    override fun getStatusCode(): Int {
        return statusCode
    }

    override fun <T> getBody(clazz: Class<T>): T {
        return body!!.let {
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

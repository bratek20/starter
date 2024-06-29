package com.github.bratek20.infrastructure.httpclient.impl

import com.github.bratek20.architecture.serialization.api.SerializationType
import com.github.bratek20.architecture.serialization.api.SerializedValue
import com.github.bratek20.architecture.serialization.api.Serializer
import com.github.bratek20.architecture.serialization.context.SerializationFactory
import com.github.bratek20.infrastructure.httpclient.api.*
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate

class HttpClientFactoryLogic: HttpClientFactory {
    override fun create(baseUrl: String): HttpClient {
        return HttpClientLogic(baseUrl)
    }
}

class HttpClientLogic(
    private val baseUrl: String,
) : HttpClient {
    private val restTemplate: RestTemplate = RestTemplate()

    override fun get(path: String): HttpResponse {
        val responseEntity: ResponseEntity<String> = restTemplate.exchange(
            getFullUrl(path),
            HttpMethod.GET,
            null,
            String::class.java
        )

        return HttpResponseLogic(responseEntity.statusCode.value(), responseEntity.body)
    }

    override fun <T> post(path: String, body: T): HttpResponse {
        val responseEntity: ResponseEntity<String> = restTemplate.exchange(
            getFullUrl(path),
            HttpMethod.POST,
            HttpEntity(body),
            String::class.java
        )

        return HttpResponseLogic(responseEntity.statusCode.value(), responseEntity.body)
    }

    private fun getFullUrl(path: String): String {
        return baseUrl + path
    }
}

class HttpResponseLogic(
    private val statusCode: Int,
    private val body: String?,
) : HttpResponse {
    override fun getStatusCode(): Int {
        return statusCode
    }

    override fun <T> getBody(clazz: Class<T>): T? {
        return body?.let {
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

package com.github.bratek20.infrastructure.httpclient.tests

import com.github.bratek20.architecture.context.someContextBuilder
import com.github.bratek20.architecture.exceptions.assertApiExceptionThrown
import com.github.bratek20.infrastructure.httpclient.api.*
import com.github.bratek20.infrastructure.httpclient.context.HttpClientBaseImpl
import com.github.bratek20.infrastructure.httpclient.context.HttpClientImpl
import com.github.bratek20.infrastructure.httpclient.fixtures.*
import com.github.bratek20.logs.LoggerMock
import com.github.bratek20.logs.LogsMocks
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class HttpRequesterMock: HttpRequester {
    private var lastRequest: HttpRequest? = null

    var response: SendResponseDef.() -> Unit = {}

    override fun send(request: HttpRequest): SendResponse {
        lastRequest = request
        return sendResponse(response)
    }

    fun assertLastRequest(expected: ExpectedHttpRequest.() -> Unit) {
        assertHttpRequest(lastRequest!!, expected)
    }
}

class HttpClientImplTest {
    private lateinit var factory: HttpClientFactory

    private lateinit var requesterMock: HttpRequesterMock

    @BeforeEach
    fun setup() {
        val c = someContextBuilder()
            .withModules(
                HttpClientBaseImpl()
            )
            .setImpl(HttpRequester::class.java, HttpRequesterMock::class.java)
            .build()

        factory = c.get(HttpClientFactory::class.java)

        requesterMock = c.get(HttpRequesterMock::class.java)
    }

    private fun createClient(config: HttpClientConfigDef.() -> Unit = {}): HttpClient {
        return factory.create(
            httpClientConfig(config)
        )
    }

    data class SomeRequest(
        private val value: String
    ) {
        companion object {
            fun create(value: String) = SomeRequest(value)
        }
    }

    data class SomeResponse(val value: String) {
    }

    @Test
    fun `should support POST with Auth Header`() {
        val client = createClient {
            baseUrl = "http://localhost:8080"
            auth = {
                username = "user"
                password = "password"
            }
        }

        requesterMock.response = {
            statusCode = 200
            body = "{\"value\": \"response value\"}"
        }

        val request = SomeRequest.create("request value")
        val response = client.post("/test", request)

        assertThat(response.getStatusCode()).isEqualTo(200)

        val responseBody = response.getBody(SomeResponse::class.java)
        assertThat(responseBody.value).isEqualTo("response value")

        requesterMock.assertLastRequest {
            url = "http://localhost:8080/test"
            method = "POST"
            content = "{\"value\":\"request value\"}"
            contentType = "application/json"
            headers = listOf {
                key = "Authorization"
                value = "Basic dXNlcjpwYXNzd29yZA=="
            }
        }
    }

    val SOME_REQEUST = SomeRequest.create("request value")

    @Nested
    inner class PersistSessionScope {
        @Test
        fun `should use session cookie from first response in next requests`() {
            val client = createClient {
                persistSession = true
            }

            requesterMock.response = {
                statusCode = 200
                headers = listOf {
                    key = "Set-Cookie"
                    value = "session=123"
                }
            }
            client.post("/login", SOME_REQEUST)

            client.post("/test", SOME_REQEUST)
            requesterMock.assertLastRequest {
                headers = listOf {
                    key = "Set-Cookie"
                    value = "session=123"
                }
            }
        }
        
        @Test
        fun `should throw exception when first response does not have session cookie`() {
            val client = createClient {
                persistSession = true
            }

            assertApiExceptionThrown(
                { client.post("/test", SOME_REQEUST) },
                {
                    type = HttpClientException::class
                    message = "Session can not be persisted! Set-Cookie not found in response header for first request"
                }
            )

        }
    }
}

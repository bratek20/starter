package com.github.bratek20.infrastructure.httpclient.tests

import com.github.bratek20.architecture.context.someContextBuilder
import com.github.bratek20.architecture.exceptions.ApiException
import com.github.bratek20.architecture.exceptions.assertApiExceptionThrown
import com.github.bratek20.infrastructure.httpclient.api.HttpClient
import com.github.bratek20.infrastructure.httpclient.api.HttpClientFactory
import com.github.bratek20.infrastructure.httpclient.api.HttpResponse
import com.github.bratek20.infrastructure.httpclient.context.HttpClientImpl
import com.github.bratek20.infrastructure.httpclient.fixtures.httpClientConfig
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder
import com.github.tomakehurst.wiremock.client.WireMock
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MyException(message: String): ApiException(message)

class HttpClientImplTest {
    private lateinit var server: WireMockServer
    private lateinit var factory: HttpClientFactory
    private lateinit var client: HttpClient

    @BeforeEach
    fun setup() {
        server = WireMockServer(8080)
        server.start()

        factory = someContextBuilder()
            .withModules(HttpClientImpl())
            .buildAndGet(HttpClientFactory::class.java)

        client = factory.create(httpClientConfig {
            baseUrl = "http://localhost:8080"
        })
    }

    @AfterEach
    fun clean() {
        server.stop()
    }

    data class SomeValue(val value: String)

    data class SomeClass(
        private val value: String,
        private val amount: Int
    ) {
        fun getValue(): SomeValue {
            return SomeValue(this.value)
        }

        fun getAmount(): Int {
            return this.amount
        }

        companion object {
            fun create(
                value: SomeValue,
                amount: Int
            ): SomeClass {
                return SomeClass(
                    value = value.value,
                    amount = amount
                )
            }
        }
    }

    private fun stub(
        testUrl: String,
        post: Boolean = false,
        withPostRequestBody: Boolean = true
    ) {
        var mapping = WireMock.get(WireMock.urlEqualTo(testUrl));
        if (post) {
            mapping = WireMock.post(WireMock.urlEqualTo("/post"))

            if (withPostRequestBody) {
                mapping.withRequestBody(WireMock.equalToJson("{\"value\": \"Some request\", \"amount\": 1}"))
            }
        }

        server.stubFor(
            mapping
                .willReturn(
                    wireMockResponse()
                )
        )
    }

    private fun requestBody(): SomeClass {
        return SomeClass.create(
            value = SomeValue("Some request"),
            amount = 1
        )
    }

    private fun wireMockResponse(): ResponseDefinitionBuilder {
        return WireMock.aResponse()
            .withHeader("Content-Type", "application/json")
            .withStatus(200)
            .withBody("{\"value\": \"Some response\", \"amount\": 2}")
    }

    private fun responseBody(): SomeClass {
        return SomeClass.create(
            value = SomeValue("Some response"),
            amount = 2
        )
    }

    private fun assertResponse(response: HttpResponse) {
        assertThat(response.getStatusCode()).isEqualTo(200)
        assertThat(response.getBody(SomeClass::class.java))
            .isEqualTo(responseBody())
    }

    @Test
    fun shouldSupportGet() {
        stub(
            testUrl = "/get",
        )

        val response = client.get("/get")

        assertResponse(response)
    }

    @Test
    fun shouldSupportPost() {
        stub(
            testUrl = "/post",
            post = true,
        )

        val response = client.post("/post", requestBody())

        assertResponse(response)
    }

    @Test
    fun shouldSupportPostNullBody() {
        stub(
            testUrl = "/post",
            post = true,
            withPostRequestBody = false
        )

        val response = client.post("/post", null)

        assertResponse(response)
    }

    @Test
    fun shouldThrowPassedException() {
        server.stubFor(
            WireMock.post(WireMock.urlEqualTo("/throw"))
                .willReturn(
                    WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBody("""
                            {
                                "passedException": {
                                    "type": "MyException",
                                    "package": "com.github.bratek20.infrastructure.httpclient.tests",
                                    "message": "Some message"
                                }
                            }
                        """)
                )
        )

        assertApiExceptionThrown(
            {client.post("/throw", requestBody())},
            {
                type = MyException::class
                message = "Some message"
            }
        )
    }

    @Test
    fun shouldSendAuthHeaderIfAuthInConfigPresent() {
        server.stubFor(WireMock.get(WireMock.urlEqualTo("/getAuth"))
            .withHeader("Authorization", WireMock.equalTo("Basic dXNlcjpwYXNzd29yZA==")) // "user:password" base64 encoded
            .willReturn(
                wireMockResponse()
            )
        )

        client = factory.create(httpClientConfig {
            baseUrl = "http://localhost:8080"
            auth = {
                username = "user"
                password = "password"
            }
        })

        val response = client.get("/getAuth")

        assertResponse(response)
    }
}
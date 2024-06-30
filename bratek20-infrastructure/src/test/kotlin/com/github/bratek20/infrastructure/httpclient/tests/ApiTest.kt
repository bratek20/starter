package com.github.bratek20.infrastructure.httpclient.tests

import com.github.bratek20.architecture.context.someContextBuilder
import com.github.bratek20.infrastructure.httpclient.api.HttpClient
import com.github.bratek20.infrastructure.httpclient.api.HttpClientFactory
import com.github.bratek20.infrastructure.httpclient.context.HttpClientImpl
import com.github.bratek20.infrastructure.httpclient.impl.HttpClientFactoryLogic
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class HttpClientApiTest {
    private lateinit var server: WireMockServer
    private lateinit var client: HttpClient

    @BeforeEach
    fun setup() {
        server = WireMockServer(8080)
        server.start()

        client = someContextBuilder()
            .withModules(HttpClientImpl())
            .get(HttpClientFactory::class.java)
            .create("http://localhost:8080")
    }

    @AfterEach
    fun clean() {
        server.stop()
    }

    data class ExampleBody(val message: String)

    @Test
    fun shouldSupportGet() {
        server.stubFor(
            WireMock.get(WireMock.urlEqualTo("/get"))
                .willReturn(
                    WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBody("{\"message\": \"Hello World\"}")
                )
        )

        val response = client.get("/get")

        Assertions.assertThat(response.getStatusCode()).isEqualTo(200)
        Assertions.assertThat(response.getBody(ExampleBody::class.java))
            .isEqualTo(ExampleBody("Hello World"))
    }

    @Test
    fun shouldSupportPost() {
        server.stubFor(
            WireMock.post(WireMock.urlEqualTo("/post"))
                .withRequestBody(WireMock.equalToJson("{\"message\": \"Request\"}"))
                .willReturn(
                    WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBody("{\"message\": \"Response\"}")
                )
        )

        val response = client.post("/post", ExampleBody("Request"))

        Assertions.assertThat(response.getStatusCode()).isEqualTo(200)
        Assertions.assertThat(response.getBody(ExampleBody::class.java))
            .isEqualTo(ExampleBody("Response"))
    }

    @Test
    fun shouldSupportPostNullBody() {
        server.stubFor(
            WireMock.post(WireMock.urlEqualTo("/post"))
                //.withRequestBody(WireMock.equalToJson("{\"message\": \"Request\"}"))
                .willReturn(
                    WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBody("{\"message\": \"Response\"}")
                )
        )

        val response = client.post("/post", null)

        Assertions.assertThat(response.getStatusCode()).isEqualTo(200)
        Assertions.assertThat(response.getBody(ExampleBody::class.java))
            .isEqualTo(ExampleBody("Response"))
    }
}
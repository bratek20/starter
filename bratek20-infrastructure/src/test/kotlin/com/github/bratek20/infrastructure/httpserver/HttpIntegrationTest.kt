package com.github.bratek20.infrastructure.httpserver

import com.github.bratek20.architecture.context.api.ContextBuilder
import com.github.bratek20.architecture.context.api.ContextModule
import com.github.bratek20.architecture.context.someContextBuilder
import com.github.bratek20.architecture.exceptions.ApiException
import com.github.bratek20.architecture.exceptions.assertApiExceptionThrown
import com.github.bratek20.architecture.serialization.api.Serializer
import com.github.bratek20.architecture.serialization.api.Struct
import com.github.bratek20.architecture.serialization.context.SerializationFactory
import com.github.bratek20.infrastructure.httpclient.api.HttpClientConfig
import com.github.bratek20.infrastructure.httpclient.api.HttpClientFactory
import com.github.bratek20.infrastructure.httpclient.context.HttpClientImpl
import com.github.bratek20.infrastructure.httpclient.fixtures.httpClientConfig
import com.github.bratek20.infrastructure.httpserver.api.WebServerModule
import com.github.bratek20.infrastructure.httpserver.fixtures.TestWebApp
import com.github.bratek20.infrastructure.httpserver.fixtures.runTestWebApp
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

class SomeException(message: String): ApiException(message)

class HttpIntegrationTest {
    data class SomeValue(val value: String)

    data class SomeClass(
        private val value: String
    ) {
        fun getValue(): SomeValue {
            return SomeValue(this.value)
        }
    }

    data class SomeApiMirrorRequest(val x: SomeClass)
    data class SomeApiMirrorResponse(val value: SomeClass)

    interface SomeApi {
        fun mirror(x: SomeClass): SomeClass

        @Throws(SomeException::class)
        fun throwException()
    }

    class SomeApiLogic: SomeApi {
        override fun mirror(x: SomeClass): SomeClass {
            return x
        }

        override fun throwException() {
            throw SomeException("Some message")
        }
    }

    data class SomeApiWebClientConfig(
        val value: HttpClientConfig
    )
    class SomeApiWebClient(
        config: SomeApiWebClientConfig,
        factory: HttpClientFactory,
    ): SomeApi {
        private val client = factory.create(config.value)

        override fun mirror(x: SomeClass): SomeClass {
            return client.post("/mirror", SomeApiMirrorRequest(x)).getBody(SomeApiMirrorResponse::class.java).value
        }

        override fun throwException() {
            client.post("/throw", null)
        }
    }

    @RestController
    class SomeApiController(
        private val someApi: SomeApi
    ) {
        private val serializer: Serializer = SerializationFactory.createSerializer()

        @PostMapping("/mirror")
        fun mirror(@RequestBody rawRequest: Struct): Struct {
            val request = serializer.fromStruct(rawRequest, SomeApiMirrorRequest::class.java)
            return serializer.asStruct(SomeApiMirrorResponse(someApi.mirror(request.x)))
        }

        @PostMapping("/throw")
        fun throwException() {
            someApi.throwException()
        }
    }

    class SomeImpl: ContextModule {
        override fun apply(builder: ContextBuilder) {
            builder.setImpl(SomeApi::class.java, SomeApiLogic::class.java)
        }
    }

    class SomeWebServer: WebServerModule {
        override fun getControllers(): List<Class<*>> {
            return listOf(
                SomeApiController::class.java
            )
        }

        override fun apply(builder: ContextBuilder) {
            builder.withModule(SomeImpl())
        }
    }

    class SomeWebClient(
        private val config: SomeApiWebClientConfig
    ): ContextModule {
        override fun apply(builder: ContextBuilder) {
            builder
                .setImplObject(SomeApiWebClientConfig::class.java, config)
                .setImpl(SomeApi::class.java, SomeApiWebClient::class.java)
        }
    }

    @Test
    fun `should handle web layer hiding`() {
        //server
        val app = runTestWebApp(
            modules = listOf(
                SomeWebServer()
            )
        )

        //client
        val api = someContextBuilder()
            .withModules(
                HttpClientImpl(),
                SomeWebClient(
                    config = SomeApiWebClientConfig(
                        httpClientConfig {
                            baseUrl = "http://localhost:${app.port}"
                        }
                    )
                )
            )
            .buildAndGet(SomeApi::class.java)

        //when & then
        assertThat(api.mirror(SomeClass("test")))
            .isEqualTo(SomeClass("test"))

        assertApiExceptionThrown(
            { api.throwException() },
            {
                type = SomeException::class
                message = "Some message"
            }
        )
        app.loggerMock.assertWarns(
            "Passing exception `SomeException` with message `Some message`"
        )
    }
}
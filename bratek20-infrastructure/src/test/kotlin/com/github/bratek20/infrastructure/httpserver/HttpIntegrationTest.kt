package com.github.bratek20.infrastructure.httpserver

import com.github.bratek20.architecture.context.api.ContextBuilder
import com.github.bratek20.architecture.context.api.ContextModule
import com.github.bratek20.architecture.context.someContextBuilder
import com.github.bratek20.architecture.exceptions.ApiException
import com.github.bratek20.architecture.exceptions.assertApiExceptionThrown
import com.github.bratek20.architecture.serialization.api.Serializer
import com.github.bratek20.architecture.serialization.api.Struct
import com.github.bratek20.architecture.serialization.context.SerializationFactory
import com.github.bratek20.infrastructure.httpclient.api.HttpClient
import com.github.bratek20.infrastructure.httpclient.api.HttpClientFactory
import com.github.bratek20.infrastructure.httpclient.context.HttpClientImpl
import com.github.bratek20.infrastructure.httpserver.api.WebServerModule
import com.github.bratek20.infrastructure.httpserver.fixtures.TestWebApp
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

//TODO move to inner class when test are passing to check if it's still working
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

    data class SomeWebServerUrl(
        val value: String
    )
    class SomeApiWebClient(
        private val url: SomeWebServerUrl,
        private val factory: HttpClientFactory,
    ): SomeApi {
        override fun mirror(x: SomeClass): SomeClass {
            return factory.create(url.value).post("/mirror", SomeApiMirrorRequest(x)).getBody(SomeApiMirrorResponse::class.java).value
        }

        override fun throwException() {
            factory.create(url.value).post("/throw", null)
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
        override fun getImpl(): ContextModule {
            return SomeImpl()
        }

        override fun getControllers(): List<Class<*>> {
            return listOf(
                SomeApiController::class.java
            )
        }
    }

    class SomeWebClient(
        private val serverUrl: String = "SOME_WEB_SERVER_URL"
    ): ContextModule {
        override fun apply(builder: ContextBuilder) {
            builder
                .setImplObject(SomeWebServerUrl::class.java, SomeWebServerUrl(serverUrl))
                .setImpl(SomeApi::class.java, SomeApiWebClient::class.java)
        }
    }

    @Test
    fun `should handle web layer hiding`() {
        //server
        val serverPort = TestWebApp(
            modules = listOf(
                SomeWebServer()
            )
        ).run().port

        //client
        val api = someContextBuilder()
            .withModules(
                HttpClientImpl(),
                SomeWebClient(
                    serverUrl = "http://localhost:$serverPort"
                )
            )
            .get(SomeApi::class.java)

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
    }
}
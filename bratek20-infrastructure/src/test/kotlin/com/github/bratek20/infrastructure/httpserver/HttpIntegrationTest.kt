package com.github.bratek20.infrastructure.httpserver

import com.github.bratek20.architecture.context.api.ContextBuilder
import com.github.bratek20.architecture.context.api.ContextModule
import com.github.bratek20.architecture.context.someContextBuilder
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

class HttpIntegrationTest {
    data class SomeValue(val value: String)

    interface SomeApi {
        fun mirror(x: SomeValue): SomeValue
    }

    class SomeApiLogic: SomeApi {
        override fun mirror(x: SomeValue): SomeValue {
            return x
        }
    }

    data class SomeWebServerUrl(
        val value: String
    )
    class SomeApiWebClient(
        private val url: SomeWebServerUrl,
        private val factory: HttpClientFactory,
    ): SomeApi {
        override fun mirror(x: SomeValue): SomeValue {
            return factory.create(url.value).post("/mirror", x).getBody(SomeValue::class.java)
        }
    }

    @RestController
    class SomeApiController(
        private val someApi: SomeApi,
    ) {
        @PostMapping("/mirror")
        fun mirror(@RequestBody x: SomeValue): SomeValue {
            return someApi.mirror(x)
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

        //when
        val result = api.mirror(SomeValue("test"))

        //then
        assertThat(result).isEqualTo(SomeValue("test"))
    }
}
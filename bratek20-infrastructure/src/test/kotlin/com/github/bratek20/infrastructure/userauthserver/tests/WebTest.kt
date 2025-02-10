package com.github.bratek20.infrastructure.userauthserver.tests

import com.github.bratek20.architecture.context.api.ContextBuilder
import com.github.bratek20.architecture.context.api.ContextModule
import com.github.bratek20.architecture.context.someContextBuilder
import com.github.bratek20.architecture.data.context.DataImpl
import com.github.bratek20.architecture.data.context.DataInMemoryImpl
import com.github.bratek20.infrastructure.httpclient.context.HttpClientImpl
import com.github.bratek20.infrastructure.httpclient.fixtures.httpClientConfig
import com.github.bratek20.infrastructure.httpserver.HttpIntegrationTest
import com.github.bratek20.infrastructure.httpserver.api.WebServerModule
import com.github.bratek20.infrastructure.httpserver.fixtures.runTestWebApp
import com.github.bratek20.infrastructure.userauthserver.api.UserAuthServerApi
import com.github.bratek20.infrastructure.userauthserver.api.UserId
import com.github.bratek20.infrastructure.userauthserver.api.UserSession
import com.github.bratek20.infrastructure.userauthserver.context.UserAuthServerWebClient
import com.github.bratek20.infrastructure.userauthserver.context.UserAuthServerWebServer
import com.github.bratek20.infrastructure.userauthserver.fixtures.assertUserMapping
import org.junit.jupiter.api.Test
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

class UserAuthServerWebTest {
    @RestController
    class SomeUserModuleController(
        private val userSession: UserSession
    ) {
        @PostMapping("/getUserId")
        fun getUserId(): UserId {
            return userSession.getUserId()
        }
    }

    class SomeImpl: ContextModule {
        override fun apply(builder: ContextBuilder) {
            builder.setImpl(HttpIntegrationTest.SomeApi::class.java, HttpIntegrationTest.SomeApiLogic::class.java)
        }
    }

    class SomeUserModuleWebServer: WebServerModule {
        override fun getControllers(): List<Class<*>> {
            return listOf(
                SomeUserModuleController::class.java
            )
        }

        override fun apply(builder: ContextBuilder) {
        }
    }

    @Test
    fun `should login and start session`() {
        val app = runTestWebApp(
            modules = listOf(
                DataInMemoryImpl(),
                UserAuthServerWebServer(),
                SomeUserModuleWebServer(),
            )
        )

        val api = someContextBuilder()
            .withModules(
                HttpClientImpl(),
                UserAuthServerWebClient(
                    config = httpClientConfig {
                        baseUrl = "http://localhost:${app.port}"
                        persistSession = true
                    }
                )
            )
            .buildAndGet(UserAuthServerApi::class.java)

        val userId = api.createUserAndLogin().getUserId()
    }
}
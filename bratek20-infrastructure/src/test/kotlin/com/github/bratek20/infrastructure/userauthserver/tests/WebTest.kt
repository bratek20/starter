package com.github.bratek20.infrastructure.userauthserver.tests

import com.github.bratek20.architecture.context.api.ContextBuilder
import com.github.bratek20.architecture.context.someContextBuilder
import com.github.bratek20.architecture.data.context.DataInMemoryImpl
import com.github.bratek20.infrastructure.httpclient.api.HttpClientConfig
import com.github.bratek20.infrastructure.httpclient.api.HttpClientFactory
import com.github.bratek20.infrastructure.httpclient.context.HttpClientImpl
import com.github.bratek20.infrastructure.httpclient.fixtures.httpClientConfig
import com.github.bratek20.infrastructure.httpserver.api.WebServerModule
import com.github.bratek20.infrastructure.httpserver.fixtures.runTestWebApp
import com.github.bratek20.infrastructure.userauthserver.api.UserAuthServerApi
import com.github.bratek20.infrastructure.userauthserver.api.UserId
import com.github.bratek20.infrastructure.userauthserver.api.UserSession
import com.github.bratek20.infrastructure.userauthserver.context.UserAuthServerWebClient
import com.github.bratek20.infrastructure.userauthserver.context.UserAuthServerWebServer
import com.github.bratek20.infrastructure.userauthserver.context.UserSessionConfig
import com.github.bratek20.infrastructure.userauthserver.fixtures.assertUserId
import com.github.bratek20.infrastructure.userauthserver.impl.UserSessionLogic
import org.junit.jupiter.api.Test
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.annotation.SessionScope

class UserAuthServerWebTest {
    @Component
    class SomeUserModuleLogic(
        private val userSession: UserSession
    ) {
        fun getUserId(): UserId {
            return userSession.getUserId()
        }
    }

    @Configuration
    open class SomeUserModuleSessionConfig {
        @Bean
        @SessionScope
        open fun someUserModuleLogic(userSession: UserSession): SomeUserModuleLogic {
            return SomeUserModuleLogic(userSession)
        }
    }

    @RestController
    class SomeUserModuleController(
        private val logic: SomeUserModuleLogic,
        private val userSession: UserSession
    ) {
        @PostMapping("/getUserId")
        fun getUserId(): UserId {
            return logic.getUserId()
        }

        @PostMapping("/getUserIdFromSession")
        fun getUserIdFromSession(): UserId {
            return userSession.getUserId()
        }
    }

    data class SomeUserModuleWebClientConfig(
        val value: HttpClientConfig
    )
    class SomeUserModuleWebClient(
        config: SomeUserModuleWebClientConfig,
        factory: HttpClientFactory,
    ) {
        private val client = factory.create(config.value)

        fun getUserId(): UserId {
            return client.post("/getUserId", null).getBody(UserId::class.java)
        }

        fun getUserIdFromSession(): UserId {
            return client.post("/getUserIdFromSession", null).getBody(UserId::class.java)
        }
    }

    class SomeUserModuleWebServer: WebServerModule {
        override fun getControllers(): List<Class<*>> {
            return listOf(
                SomeUserModuleController::class.java
            )
        }

        override fun getConfigs(): List<Class<*>> {
            return listOf(
                SomeUserModuleSessionConfig::class.java
            )
        }

        override fun apply(builder: ContextBuilder) {
            //builder.setClass(SomeUserModuleLogic::class.java)
        }
    }

    @Test
    fun `should login and start session`() {
        val app = runTestWebApp(
            modules = listOf(
                DataInMemoryImpl(),
                UserAuthServerWebServer(),
                SomeUserModuleWebServer(),
            ),
        )

        val c = createClient(app.port)

        //create
        val mapping = c.userAuthServerApi.createUserAndLogin()
        assertUserId(mapping.getUserId(), 1)

        val userIdFromSession = c.someUserModuleWebClient.getUserIdFromSession()
        assertUserId(userIdFromSession, 1)

        val userId = c.someUserModuleWebClient.getUserId()
        assertUserId(userId, 1)

        //login again
        val c2 = createClient(app.port)
        val userId2 = c2.userAuthServerApi.login(mapping.getAuthId())
        assertUserId(userId2, 1)

        //different user
        val c3 = createClient(app.port)
        val userId3 = c3.userAuthServerApi.createUserAndLogin().getUserId()
        assertUserId(userId3, 2)
    }

    data class ClientApis(
        val userAuthServerApi: UserAuthServerApi,
        val someUserModuleWebClient: SomeUserModuleWebClient
    )
    private fun createClient(port: Int): ClientApis {
        val httpConfig = httpClientConfig {
            baseUrl = "http://localhost:$port"
            persistSession = true
        }
        val c = someContextBuilder()
            .withModules(
                HttpClientImpl(),
                UserAuthServerWebClient(
                    config = httpConfig
                )
            )
            .setImplObject(SomeUserModuleWebClientConfig::class.java, SomeUserModuleWebClientConfig(httpConfig))
            .setClass(SomeUserModuleWebClient::class.java)
            .build()

        return ClientApis(
            userAuthServerApi = c.get(UserAuthServerApi::class.java),
            someUserModuleWebClient = c.get(SomeUserModuleWebClient::class.java)
        )
    }
}
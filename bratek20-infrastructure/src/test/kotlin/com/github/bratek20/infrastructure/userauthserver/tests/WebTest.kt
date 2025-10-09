package com.github.bratek20.infrastructure.userauthserver.tests

import com.github.bratek20.architecture.context.api.ContextBuilder
import com.github.bratek20.architecture.context.someContextBuilder
import com.github.bratek20.architecture.data.api.DataStorage
import com.github.bratek20.architecture.data.api.ObjectDataKey
import com.github.bratek20.architecture.data.context.DataInMemoryImpl
import com.github.bratek20.architecture.users.api.User
import com.github.bratek20.architecture.users.api.UserDataStorage
import com.github.bratek20.architecture.users.api.UserId
import com.github.bratek20.architecture.users.fixtures.assertUserId
import com.github.bratek20.infrastructure.httpclient.api.HttpClientConfig
import com.github.bratek20.infrastructure.httpclient.api.HttpClientFactory
import com.github.bratek20.infrastructure.httpclient.context.HttpClientImpl
import com.github.bratek20.infrastructure.httpclient.fixtures.httpClientConfig
import com.github.bratek20.infrastructure.httpserver.api.WebServerModule
import com.github.bratek20.infrastructure.httpserver.fixtures.runTestWebApp
import com.github.bratek20.infrastructure.sessionuser.SessionDataConfig
import com.github.bratek20.infrastructure.userauthserver.api.UserAuthServerApi
import com.github.bratek20.infrastructure.sessionuser.SessionComponent
import com.github.bratek20.infrastructure.userauthserver.fixtures.assertUserMapping
import com.github.bratek20.infrastructure.userauthserver.web.UserAuthServerWebClient
import com.github.bratek20.infrastructure.userauthserver.web.UserAuthServerWebServer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

class UserAuthServerWebTest {
    class UserData(
        var value: Int = 0
    )

    interface SomeUserModuleApi {
        fun getUserId(): UserId
        fun increaseValue(): Int
        fun increaseValueInSession(): Int
    }

    @SessionComponent
    class SomeUserModuleApiLogic(
        private val user: User,
        private val storage: UserDataStorage
    ): SomeUserModuleApi {
        override fun getUserId(): UserId {
            return user.getId()
        }

        override fun increaseValue(): Int {
            val dataKey = ObjectDataKey<UserData>("userData", UserData::class)
            val userData = storage.find(dataKey) ?: UserData()
            userData.value += 1
            storage.set(dataKey, userData)
            return userData.value
        }

        private var value: Int = 0
        override fun increaseValueInSession(): Int {
            value += 1
            return value
        }
    }

    @RestController
    class SomeUserModuleController(
        private val api: SomeUserModuleApi,
        private val user: User
    ) {
        @PostMapping("/getUserId")
        fun getUserId(): UserId {
            return api.getUserId()
        }

        @PostMapping("/getUserIdFromSession")
        fun getUserIdFromSession(): UserId {
            return user.getId()
        }

        @PostMapping("/increaseValue")
        fun increaseValue(): Int {
            return api.increaseValue()
        }

        @PostMapping("/increaseValueInSession")
        fun increaseValueInSession(): Int {
            return api.increaseValueInSession()
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

        fun increaseValue(): Int {
            return client.post("/increaseValue", null).getBody(Int::class.java)
        }

        fun increaseValueInSession(): Int {
            return client.post("/increaseValueInSession", null).getBody(Int::class.java)
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
                SomeUserModuleApiLogic::class.java
            )
        }

        override fun apply(builder: ContextBuilder) {

        }
    }

    @Test
    fun `should login and start session + session data storage`() {
        val app = runTestWebApp(
            modules = listOf(
                DataInMemoryImpl(),
                UserAuthServerWebServer(),
                SomeUserModuleWebServer(),
            ),
            configs = listOf(
                SessionDataConfig::class.java
            )
        )

        val c = createClient(app.port)

        //create
        val mapping = c.userAuthServerApi.createUserAndLogin()
        assertUserId(mapping.getUserId(), 1)

        val userIdFromSession = c.someUserModuleWebClient.getUserIdFromSession()
        assertUserId(userIdFromSession, 1)

        val user1Id = c.someUserModuleWebClient.getUserId()
        assertUserId(user1Id, 1)

        //login again
        val c2 = createClient(app.port)
        val user2 = c2.userAuthServerApi.login(mapping.getAuthId())
        assertUserMapping(user2) {
            userId = 1
        }

        //different user
        val c3 = createClient(app.port)
        val userId3 = c3.userAuthServerApi.createUserAndLogin().getUserId()
        assertUserId(userId3, 2)

        //data storage
        val c1Inc = c.someUserModuleWebClient.increaseValue()
        assertThat(c1Inc).isEqualTo(1)

        val c2Inc = c2.someUserModuleWebClient.increaseValue()
        assertThat(c2Inc).isEqualTo(2)

        val c3Inc = c3.someUserModuleWebClient.increaseValue()
        assertThat(c3Inc).isEqualTo(1)

        val appStorage = app.context.get(DataStorage::class.java)
        appStorage.get(ObjectDataKey("user1.userData", UserData::class)).let {
            assertThat(it.value).isEqualTo(2)
        }
        appStorage.get(ObjectDataKey("user2.userData", UserData::class)).let {
            assertThat(it.value).isEqualTo(1)
        }

        //session data
        val c1IncSession = c.someUserModuleWebClient.increaseValueInSession()
        assertThat(c1IncSession).isEqualTo(1)
        val c1IncSession2 = c.someUserModuleWebClient.increaseValueInSession()
        assertThat(c1IncSession2).isEqualTo(2)

        val c2IncSession = c2.someUserModuleWebClient.increaseValueInSession()
        assertThat(c2IncSession).isEqualTo(1)

        val c3IncSession = c3.someUserModuleWebClient.increaseValueInSession()
        assertThat(c3IncSession).isEqualTo(1)
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
package com.github.bratek20.infrastructure.userauthclient.tests

import com.github.bratek20.architecture.context.someContextBuilder
import com.github.bratek20.architecture.data.api.DataStorage
import com.github.bratek20.architecture.data.context.DataInMemoryImpl
import com.github.bratek20.architecture.exceptions.assertApiExceptionThrown
import com.github.bratek20.architecture.users.fixtures.assertUserId
import com.github.bratek20.infrastructure.userauthclient.api.NotLoggedInException
import com.github.bratek20.infrastructure.userauthclient.api.UserAuthClientApi
import com.github.bratek20.infrastructure.userauthclient.context.UserAuthClientImpl
import com.github.bratek20.infrastructure.userauthserver.api.UserAuthServerApi
import com.github.bratek20.infrastructure.userauthserver.fixtures.UserAuthServerApiMock
import com.github.bratek20.infrastructure.userauthserver.fixtures.authId
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class UserAuthClientImplTest {
    private lateinit var api: UserAuthClientApi
    private lateinit var serverApiMock: UserAuthServerApiMock

    @BeforeEach
    fun setup() {
        val c = someContextBuilder()
            .withModules(
                DataInMemoryImpl(),
                UserAuthClientImpl()
            )
            .setImpl(UserAuthServerApi::class.java, UserAuthServerApiMock::class.java)
            .build()

        api = c.get(UserAuthClientApi::class.java)
        serverApiMock = c.get(UserAuthServerApiMock::class.java)
    }

    @Test
    fun `should throw exception if not logged`() {
        assertApiExceptionThrown(
            { api.getUserId() },
            {
                type = NotLoggedInException::class
            }
        )
    }

    @Test
    fun `should create new user on first login`() {
        serverApiMock.setCreateUserAndLoginResponse {
            userId = 1
        }

        api.login()

        assertUserId(api.getUserId(), 1)
    }

    @Test
    fun `on next login should use auth from last login`() {
        serverApiMock.setCreateUserAndLoginResponse {
            authId = "abc"
        }
        api.login()

        serverApiMock.setLoginResponse {
            authId = "cde"
        }
        api.login()

        api.login()

        serverApiMock.assertLoginCalls(listOf(
            "abc",
            "cde"
        ))
    }
}
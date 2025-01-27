package com.github.bratek20.infrastructure.userauthserver.tests

import com.github.bratek20.architecture.context.someContextBuilder
import com.github.bratek20.architecture.data.context.DataInMemoryImpl
import com.github.bratek20.architecture.exceptions.assertApiExceptionThrown
import com.github.bratek20.infrastructure.userauthserver.api.*
import com.github.bratek20.infrastructure.userauthserver.context.UserAuthServerBaseImpl
import com.github.bratek20.infrastructure.userauthserver.fixtures.assertUserId
import com.github.bratek20.infrastructure.userauthserver.fixtures.assertUserMapping
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class UserAuthServerImplTest {
    class AuthIdGeneratorMock: AuthIdGenerator {
        var nextAuthId: String = "someId"

        override fun generate(): AuthId {
            return AuthId(nextAuthId)
        }
    }

    private lateinit var api: UserAuthServerApi

    private lateinit var authIdGeneratorMock: AuthIdGeneratorMock

    @BeforeEach
    fun setup() {
        val c = someContextBuilder()
            .withModules(
                DataInMemoryImpl(),
                UserAuthServerBaseImpl()
            )
            .setImpl(AuthIdGenerator::class.java, AuthIdGeneratorMock::class.java)
            .build()

        api = c.get(UserAuthServerApi::class.java)
        authIdGeneratorMock = c.get(AuthIdGeneratorMock::class.java)
    }

    @Test
    fun `should create new users`() {
        authIdGeneratorMock.nextAuthId = "abc"
        assertUserMapping(api.createUserAndLogin()) {
            authId = "abc"
            userId = 1
        }

        authIdGeneratorMock.nextAuthId = "def"
        assertUserMapping(api.createUserAndLogin()) {
            authId = "def"
            userId = 2
        }
    }

    @Test
    fun `should login to existing user`() {
        val authId = api.createUserAndLogin().getAuthId()

        assertUserId(api.login(authId), 1)
    }

    @Test
    fun `should throw when trying to login with not known auth id`() {
        assertApiExceptionThrown(
            { api.login(AuthId("unknown")) },
            {
                type = UserMappingNotFoundException::class
                message = "User mapping not found for authId 'unknown'"
            }
        )
    }
}
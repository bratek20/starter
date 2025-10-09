package com.github.bratek20.infrastructure.userauthserver.tests

import com.github.bratek20.architecture.context.someContextBuilder
import com.github.bratek20.architecture.data.context.DataInMemoryImpl
import com.github.bratek20.architecture.exceptions.assertApiExceptionThrown
import com.github.bratek20.architecture.users.fixtures.assertUserId
import com.github.bratek20.infrastructure.userauthserver.api.*
import com.github.bratek20.infrastructure.userauthserver.impl.UserAuthServerBaseImpl
import com.github.bratek20.infrastructure.userauthserver.fixtures.assertUserMapping
import com.github.bratek20.logs.LoggerMock
import com.github.bratek20.logs.LogsMocks
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
    private lateinit var loggerMock: LoggerMock

    @BeforeEach
    fun setup() {
        val c = someContextBuilder()
            .withModules(
                DataInMemoryImpl(),
                UserAuthServerBaseImpl(),
                LogsMocks()
            )
            .setImpl(AuthIdGenerator::class.java, AuthIdGeneratorMock::class.java)
            .build()

        api = c.get(UserAuthServerApi::class.java)
        authIdGeneratorMock = c.get(AuthIdGeneratorMock::class.java)
        loggerMock = c.get(LoggerMock::class.java)
    }

    @Test
    fun `should create new users + log info`() {
        authIdGeneratorMock.nextAuthId = "abc"
        assertUserMapping(api.createUserAndLogin()) {
            authId = "abc"
            userId = 1
        }

        loggerMock.assertInfos(
            "New user with id '1' created",
        )

        authIdGeneratorMock.nextAuthId = "def"
        assertUserMapping(api.createUserAndLogin()) {
            authId = "def"
            userId = 2
        }
    }

    @Test
    fun `should login to existing user + log info`() {
        val authId = api.createUserAndLogin().getAuthId()

        assertUserMapping(api.login(authId)) {
            userId = 1
        }

        loggerMock.assertContainsInfos(
            "Existing user '1' logged in"
        )
    }

    @Test
    fun `should throw when trying to login with not known auth id`() {
        assertApiExceptionThrown(
            { api.login(AuthId("unknown")) },
            {
                type = UnknownAuthIdException::class
                message = "User mapping not found for authId 'unknown'"
            }
        )
    }
}
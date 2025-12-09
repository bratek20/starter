package com.github.bratek20.infrastructure.userauthserver.impl

import com.github.bratek20.architecture.data.api.DataStorage
import com.github.bratek20.architecture.properties.api.Properties
import com.github.bratek20.architecture.users.api.UserId
import com.github.bratek20.infrastructure.userauthserver.api.*
import com.github.bratek20.logs.api.Logger

val USERS_MAPPING_DATA_MAP_KEY = com.github.bratek20.architecture.data.api.MapDataKey(
    "UsersMapping",
    UserMapping::class
) { it.getAuthId() }

class AuthIdGeneratorLogic: AuthIdGenerator {
    override fun generate(): AuthId {
        return AuthId(java.util.UUID.randomUUID().toString())
    }
}

class UserAuthServerApiLogic(
    private val authIdGenerator: AuthIdGenerator,
    private val storage: DataStorage,
    private val logger: Logger,
    private val properties: Properties
): UserAuthServerApi {
    init {
        if (storage.find(USERS_MAPPING_DATA_MAP_KEY) == null) {
            storage.set(USERS_MAPPING_DATA_MAP_KEY, emptyList())
        }
    }

    override fun createUserAndLogin(): UserMapping {
        val mapping = createNewUser()

        logger.info("New user with id '${mapping.getUserId()}' created")

        return mapping
    }

    private fun createNewUser(): UserMapping {
        val nextUserId = storage.get(USERS_MAPPING_DATA_MAP_KEY).size + 1
        val mapping = UserMapping.create(
            authId = authIdGenerator.generate(),
            userId = UserId(nextUserId)
        )

        storage.addElement(USERS_MAPPING_DATA_MAP_KEY, mapping)

        return mapping
    }

    override fun login(authId: AuthId): UserMapping {
        var mapping = storage.findElement(USERS_MAPPING_DATA_MAP_KEY, authId)

        if (mapping == null) {
            if (getConfig().getCreateNewUserForUnknownAuthId()) {
                mapping = createNewUser()
                logger.warn("Unknown authId '$authId' - creating new user with id '${mapping.getUserId()}'")
            } else {
                throw UnknownAuthIdException("User mapping not found for authId '$authId'")
            }
        }

        logger.info("Existing user '${mapping.getUserId()}' logged in")

        return mapping
    }

    private fun getConfig(): UserAuthServerConfig {
        return properties.find(USER_AUTH_SERVER_CONFIG_PROPERTY_KEY) ?: UserAuthServerConfig()
    }
}


package com.github.bratek20.infrastructure.userauthserver.impl

import com.github.bratek20.architecture.data.api.DataStorage
import com.github.bratek20.infrastructure.userauthserver.api.*

val USERS_MAPPING_DATA_MAP_KEY = com.github.bratek20.architecture.data.api.MapDataKey(
    "UsersMapping",
    UserMapping::class
) { it.getAuthId() }

class UserAuthServerApiLogic(
    private val authIdGenerator: AuthIdGenerator,
    private val storage: DataStorage
): UserAuthServerApi {
    init {
        if (storage.find(USERS_MAPPING_DATA_MAP_KEY) == null) {
            storage.set(USERS_MAPPING_DATA_MAP_KEY, emptyList())
        }
    }

    override fun createUserAndLogin(): UserMapping {
        val nextUserId = storage.get(USERS_MAPPING_DATA_MAP_KEY).size + 1
        val mapping = UserMapping.create(
            authId = authIdGenerator.generate(),
            userId = UserId(nextUserId)
        )

        storage.addElement(USERS_MAPPING_DATA_MAP_KEY, mapping)

        return mapping
    }

    override fun login(authId: AuthId): UserId {
        val mapping = storage.findElement(USERS_MAPPING_DATA_MAP_KEY, authId)
            ?: throw UserMappingNotFoundException("User mapping not found for authId '$authId'")
        return mapping.getUserId()
    }
}

class UserSessionLogic: UserSession {
    override fun getUserId(): UserId {
        TODO("Not yet implemented")
    }
}
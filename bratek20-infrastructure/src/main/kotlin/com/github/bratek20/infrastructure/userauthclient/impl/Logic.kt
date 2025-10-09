package com.github.bratek20.infrastructure.userauthclient.impl

import com.github.bratek20.architecture.data.api.DataStorage
import com.github.bratek20.architecture.users.api.UserId
import com.github.bratek20.infrastructure.userauthclient.api.*
import com.github.bratek20.infrastructure.userauthclient.api.NotLoggedInException

import com.github.bratek20.infrastructure.userauthserver.api.*

class UserAuthClientApiLogic(
    private val dataStorage: DataStorage,
    private val serverApi: UserAuthServerApi,
): UserAuthClientApi {
    private var loggedIn = false

    override fun login(): Unit {
        var currentMapping = dataStorage.find(USER_MAPPING_DATA_KEY)
        if (currentMapping == null) {
            currentMapping = serverApi.createUserAndLogin()
            dataStorage.set(USER_MAPPING_DATA_KEY, currentMapping)
        }
        else {
            val newMapping = serverApi.login(currentMapping.getAuthId())
            dataStorage.set(USER_MAPPING_DATA_KEY, newMapping)
        }
        loggedIn = true
    }

    override fun getUserId(): UserId {
        if (!loggedIn) {
            throw NotLoggedInException()
        }
        return dataStorage.get(USER_MAPPING_DATA_KEY).getUserId()
    }
}
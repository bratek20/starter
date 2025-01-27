package com.github.bratek20.infrastructure.userauthserver.impl

import com.github.bratek20.infrastructure.userauthserver.api.*

class UserAuthServerApiLogic: UserAuthServerApi {
    override fun createUserAndLogin(): UserMapping {
        TODO("Not yet implemented")
    }

    override fun login(authId: AuthId): UserId {
        TODO("Not yet implemented")
    }
}

class UserSessionLogic: UserSession {
    override fun getUserId(): UserId {
        TODO("Not yet implemented")
    }
}
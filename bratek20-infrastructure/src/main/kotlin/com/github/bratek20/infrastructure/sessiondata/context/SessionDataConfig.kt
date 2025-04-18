package com.github.bratek20.infrastructure.sessiondata.context

import com.github.bratek20.architecture.data.api.DataStorage
import com.github.bratek20.infrastructure.sessiondata.impl.SessionDataStorageLogic
import com.github.bratek20.infrastructure.userauthserver.api.UserSession
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.context.annotation.SessionScope

@Configuration
class SessionDataConfig {
    @Bean
    @SessionScope
    fun sessionDataStorage(
        appStorage: DataStorage,
        userSession: UserSession
    ): DataStorage {
        return SessionDataStorageLogic(
            appStorage,
            userSession
        )
    }
}
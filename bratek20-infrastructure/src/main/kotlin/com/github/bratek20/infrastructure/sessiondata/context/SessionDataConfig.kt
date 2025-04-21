package com.github.bratek20.infrastructure.sessiondata.context

import com.github.bratek20.architecture.data.api.DataManipulator
import com.github.bratek20.architecture.data.api.DataStorage
import com.github.bratek20.architecture.data.impl.DataStorageLogic
import com.github.bratek20.infrastructure.sessiondata.api.SessionDataStorage
import com.github.bratek20.infrastructure.sessiondata.impl.SessionDataStorageLogic
import com.github.bratek20.infrastructure.userauthserver.api.UserSession
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class SessionDataConfig {
    @Bean
    @Primary
    fun appDataStorage(
        integration: DataManipulator
    ): DataStorage {
        return DataStorageLogic(
            integration
        )
    }

    @Bean
    fun sessionDataStorage(
        appStorage: DataStorage,
        userSession: UserSession
    ): SessionDataStorage {
        return SessionDataStorageLogic(
            appStorage,
            userSession
        )
    }
}
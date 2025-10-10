package com.github.bratek20.infrastructure.sessionuser

import com.github.bratek20.architecture.data.api.DataManipulator
import com.github.bratek20.architecture.data.api.DataStorage
import com.github.bratek20.architecture.data.impl.DataStorageLogic
import com.github.bratek20.architecture.users.api.User
import com.github.bratek20.architecture.users.api.UserDataStorage
import com.github.bratek20.architecture.users.impl.UserDataStorageLogic
import jakarta.servlet.http.HttpSession
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
    fun user(session: HttpSession): User {
        return SessionUser(session)
    }

    @Bean
    fun userDataStorage(
        appStorage: DataStorage,
        user: User
    ): UserDataStorage {
        return UserDataStorageLogic(
            appStorage,
            user
        )
    }
}
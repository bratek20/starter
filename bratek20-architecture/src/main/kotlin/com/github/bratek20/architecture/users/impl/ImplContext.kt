package com.github.bratek20.architecture.users.impl

import com.github.bratek20.architecture.context.api.ContextBuilder
import com.github.bratek20.architecture.context.api.ContextModule
import com.github.bratek20.architecture.users.api.User
import com.github.bratek20.architecture.users.api.UserDataStorage

class UsersImpl: ContextModule {
    override fun apply(builder: ContextBuilder) {
        builder.setImpl(UserDataStorage::class.java, UserDataStorageLogic::class.java)
    }
}

class UsersInMemoryImpl: ContextModule {
    override fun apply(builder: ContextBuilder) {
        builder
            .withModule(UsersImpl())
            .setImpl(User::class.java, InMemoryUser::class.java)
    }
}
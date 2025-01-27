package com.github.bratek20.infrastructure.userauthserver.context

import com.github.bratek20.architecture.context.api.ContextBuilder
import com.github.bratek20.architecture.context.api.ContextModule

import com.github.bratek20.infrastructure.userauthserver.api.*
import com.github.bratek20.infrastructure.userauthserver.impl.*

class UserAuthServerBaseImpl: ContextModule {
    override fun apply(builder: ContextBuilder) {
        builder
            .setImpl(UserAuthServerApi::class.java, UserAuthServerApiLogic::class.java)
    }
}

class UserAuthServerImpl: ContextModule {
    override fun apply(builder: ContextBuilder) {
        builder
            .withModule(UserAuthServerBaseImpl())
            .setImpl(UserSession::class.java, UserSessionLogic::class.java)
    }
}
package com.github.bratek20.infrastructure.userauthserver.impl

import com.github.bratek20.architecture.context.api.ContextBuilder
import com.github.bratek20.architecture.context.api.ContextModule

import com.github.bratek20.infrastructure.userauthserver.api.*

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
            .setImpl(AuthIdGenerator::class.java, AuthIdGeneratorLogic::class.java)
    }
}
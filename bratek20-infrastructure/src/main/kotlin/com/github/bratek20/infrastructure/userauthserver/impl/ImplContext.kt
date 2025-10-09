package com.github.bratek20.infrastructure.userauthserver.impl

import com.github.bratek20.architecture.context.api.ContextBuilder
import com.github.bratek20.architecture.context.api.ContextModule

import com.github.bratek20.infrastructure.userauthserver.api.*

class UserAuthServerBaseImpl(
    private val properties: UserAuthServerProperties = UserAuthServerProperties(),
): ContextModule {
    override fun apply(builder: ContextBuilder) {
        builder
            .setImplObject(UserAuthServerProperties::class.java, properties)
            .setImpl(UserAuthServerApi::class.java, UserAuthServerApiLogic::class.java)
    }
}

class UserAuthServerImpl(
    private val properties: UserAuthServerProperties = UserAuthServerProperties(),
): ContextModule {
    override fun apply(builder: ContextBuilder) {
        builder
            .withModule(UserAuthServerBaseImpl(properties))
            .setImpl(AuthIdGenerator::class.java, AuthIdGeneratorLogic::class.java)
    }
}
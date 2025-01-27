package com.github.bratek20.infrastructure.userauthclient.context

import com.github.bratek20.architecture.context.api.ContextBuilder
import com.github.bratek20.architecture.context.api.ContextModule

import com.github.bratek20.infrastructure.userauthclient.api.*
import com.github.bratek20.infrastructure.userauthclient.impl.*

class UserAuthClientImpl: ContextModule {
    override fun apply(builder: ContextBuilder) {
        builder
            .setImpl(UserAuthClientApi::class.java, UserAuthClientApiLogic::class.java)
    }
}
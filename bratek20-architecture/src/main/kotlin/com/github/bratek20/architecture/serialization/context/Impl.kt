package com.github.bratek20.architecture.serialization.context

import com.github.bratek20.architecture.context.api.ContextBuilder
import com.github.bratek20.architecture.context.api.ContextModule

import com.github.bratek20.architecture.serialization.api.*
import com.github.bratek20.architecture.serialization.impl.*

class SerializationImpl: ContextModule {
    override fun apply(builder: ContextBuilder) {
        builder
            .setImpl(Serializer::class.java, SerializerLogic::class.java)
    }
}
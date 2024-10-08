package com.github.bratek20.architecture.serialization.context

import com.github.bratek20.architecture.context.api.ContextBuilder
import com.github.bratek20.architecture.context.api.ContextModule

import com.github.bratek20.architecture.serialization.api.*
import com.github.bratek20.architecture.serialization.impl.*

class SerializationImpl(
    private val config: SerializerConfig = SerializerConfig()
): ContextModule {
    override fun apply(builder: ContextBuilder) {
        builder
            .addImplObject(SerializerConfig::class.java, config)
            .setImpl(Serializer::class.java, SerializerLogic::class.java)
    }
}

class SerializationFactory {
    companion object {
        fun createSerializer(config: SerializerConfig = SerializerConfig()): Serializer {
            return SerializerLogic(config)
        }
    }
}
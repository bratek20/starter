package pl.bratek20.architecture.serialization.context

import pl.bratek20.architecture.context.api.ContextBuilder
import pl.bratek20.architecture.context.api.ContextModule

import pl.bratek20.architecture.serialization.api.*
import pl.bratek20.architecture.serialization.impl.*

class SerializationImpl: ContextModule {
    override fun apply(builder: ContextBuilder) {
        builder
            .setImpl(Serializer::class.java, SerializerLogic::class.java)
    }
}
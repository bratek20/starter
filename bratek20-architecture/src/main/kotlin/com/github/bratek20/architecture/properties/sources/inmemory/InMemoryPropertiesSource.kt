package com.github.bratek20.architecture.properties.sources.inmemory

import com.github.bratek20.architecture.context.api.ContextBuilder
import com.github.bratek20.architecture.context.api.ContextModule
import com.github.bratek20.architecture.properties.api.*
import com.github.bratek20.architecture.serialization.api.SerializedValue
import com.github.bratek20.architecture.serialization.impl.SerializerLogic
import kotlin.reflect.KClass

class InMemoryPropertiesSource(
    private val name: String
) : PropertiesSource {

    private val properties: MutableMap<TypedPropertyKey<*>, Any> = HashMap()

    fun set(key: TypedPropertyKey<*>, property: Any) {
        properties[key] = property
    }

    override fun getName(): PropertiesSourceName {
        return PropertiesSourceName(name)
    }

    override fun getAllKeys(): Set<String> {
        return properties.keys.map { it.name }.toSet()
    }

    override fun getValue(keyName: String): SerializedValue {
        val property = properties.keys.first { it.name == keyName }
        return SerializerLogic().serialize(properties[property]!!)
    }
}

class InMemoryPropertiesSourceImpl(
    private val name: String
): ContextModule {
    override fun apply(builder: ContextBuilder) {
        builder.addImplObject(PropertiesSource::class.java, InMemoryPropertiesSource(name))
    }
}

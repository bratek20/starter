package com.github.bratek20.architecture.properties

import com.github.bratek20.architecture.context.api.ContextBuilder
import com.github.bratek20.architecture.context.api.ContextModule
import com.github.bratek20.architecture.properties.api.MapPropertyKey
import com.github.bratek20.architecture.properties.api.Properties
import com.github.bratek20.architecture.properties.api.PropertiesSource
import com.github.bratek20.architecture.properties.api.PropertyKey

class PropertiesMock : Properties {
    private val values: MutableMap<PropertyKey<*>, Any> = mutableMapOf()

    override fun <T : Any> get(key: PropertyKey<T>): T {
        return values[key] as T
    }

    override fun <Id : Any, E : Any> findElement(key: MapPropertyKey<Id, E>, id: Id): E? {
        val list = get(key)
        return list.firstOrNull { key.idProvider(it) == id }
    }

    override fun addSource(source: PropertiesSource) {
        TODO("Not yet implemented")
    }

    fun <T: Any> set(key: PropertyKey<T>, value: T) {
        values[key] = value
    }
}

class PropertiesMocks : ContextModule {
    override fun apply(builder: ContextBuilder) {
        builder.setImpl(Properties::class.java, PropertiesMock::class.java)
    }
}

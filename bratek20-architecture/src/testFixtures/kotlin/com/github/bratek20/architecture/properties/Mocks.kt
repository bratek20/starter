package com.github.bratek20.architecture.properties

import com.github.bratek20.architecture.context.api.ContextBuilder
import com.github.bratek20.architecture.context.api.ContextModule
import com.github.bratek20.architecture.properties.api.MapPropertyKey
import com.github.bratek20.architecture.properties.api.Properties
import com.github.bratek20.architecture.properties.api.PropertiesSource
import com.github.bratek20.architecture.properties.api.TypedPropertyKey

class PropertiesMock : Properties {
    private val values: MutableMap<TypedPropertyKey<*>, Any> = mutableMapOf()

    override fun <T : Any> get(key: TypedPropertyKey<T>): T {
        return values[key] as T
    }

    override fun <Id : Any, E : Any> findElement(key: MapPropertyKey<Id, E>, id: Id): E? {
        val list = get(key)
        return list.firstOrNull { key.idProvider(it) == id }
    }

    override fun addSource(source: PropertiesSource) {
        TODO("Not yet implemented")
    }

    fun setProperty(key: TypedPropertyKey<*>, value: Any) {
        values.computeIfAbsent(key) { value }
    }
}

class PropertiesMocks : ContextModule {
    override fun apply(builder: ContextBuilder) {
        builder.setImpl(Properties::class.java, PropertiesMock::class.java)
    }
}

package com.github.bratek20.architecture.properties

import com.github.bratek20.architecture.context.api.ContextBuilder
import com.github.bratek20.architecture.context.api.ContextModule
import com.github.bratek20.architecture.exceptions.ApiException
import com.github.bratek20.architecture.properties.api.*
import com.github.bratek20.architecture.serialization.fixtures.asStruct
import com.github.bratek20.architecture.serialization.fixtures.asStructList

class ObjectPropertyNotSetException(message: String): ApiException(message)

class PropertiesMock : Properties {
    private val values: MutableMap<PropertyKey<*>, Any> = mutableMapOf()

    override fun <T : Any> get(key: PropertyKey<T>): T {
        val entry = findEntry(key)
        if (key is ListPropertyKey<*> && entry == null) {
            return emptyList<Any>() as T
        }
        if (key is ObjectPropertyKey<*> && entry == null) {
            throw ObjectPropertyNotSetException("No value was set in mock for object key `${key.name}`")
        }
        return entry!!.value as T
    }

    private fun findEntry(key: PropertyKey<*>): Map.Entry<PropertyKey<*>, Any>? {
        return values.entries.find { it.key.name == key.name }
    }

    override fun <Id : Any, E : Any> findElement(key: MapPropertyKey<Id, E>, id: Id): E? {
        val list = get(key)
        return list.firstOrNull { key.idProvider(it) == id }
    }

    override fun addSource(source: PropertiesSource) {
        TODO("Not yet implemented")
    }

    override fun getAll(): List<Property> {
        return values.map { (key, value) ->
            if (value is List<*>) {
                Property(key.name, asStructList(value as List<Any>))
            } else {
                Property(key.name, asStruct(value))
            }
        }
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

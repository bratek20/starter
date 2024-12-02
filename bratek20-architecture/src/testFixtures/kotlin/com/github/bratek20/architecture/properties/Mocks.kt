package com.github.bratek20.architecture.properties

import com.github.bratek20.architecture.context.api.ContextBuilder
import com.github.bratek20.architecture.context.api.ContextModule
import com.github.bratek20.architecture.exceptions.ApiException
import com.github.bratek20.architecture.properties.api.*

class ObjectPropertyNotSetException(message: String): ApiException(message)

class PropertiesMock : Properties {
    private val values: MutableMap<PropertyKey<*>, Any> = mutableMapOf()

    override fun <T : Any> get(key: PropertyKey<T>): T {
        if (key is ListPropertyKey<*> && !values.containsKey(key)) {
            return emptyList<Any>() as T
        }
        if (key is ObjectPropertyKey<*> && !values.containsKey(key)) {
            throw ObjectPropertyNotSetException("No value was set in mock for object key `${key.name}`")
        }
        return values[key] as T
    }

    override fun <Id : Any, E : Any> findElement(key: MapPropertyKey<Id, E>, id: Id): E? {
        val list = get(key)
        return list.firstOrNull { key.idProvider(it) == id }
    }

    override fun addSource(source: PropertiesSource) {
        TODO("Not yet implemented")
    }

    private var allSerialized: List<SerializedProperty> = emptyList()
    override fun getAllSerialized(): List<SerializedProperty> {
        return allSerialized
    }

    fun setAllSerialized(allSerialized: List<SerializedProperty>) {
        this.allSerialized = allSerialized
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

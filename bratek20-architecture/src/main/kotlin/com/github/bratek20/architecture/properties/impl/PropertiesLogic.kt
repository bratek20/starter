package com.github.bratek20.architecture.properties.impl

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.github.bratek20.architecture.exceptions.ShouldNeverHappenException
import com.github.bratek20.architecture.properties.api.*
import com.github.bratek20.architecture.serialization.api.SerializedValue
import com.github.bratek20.architecture.serialization.impl.SerializerLogic
import kotlin.reflect.KClass


class PropertiesLogic(
    private val initialSources: Set<PropertiesSource>
) : Properties {
    private val addedSources = mutableListOf<PropertiesSource>()

    private val allSources: Set<PropertiesSource>
        get() = initialSources + addedSources

    override fun <T : Any> get(key: TypedPropertyKey<T>): T {
        val source = findSourceWithKeyName(key.name)
            ?: throw PropertyNotFoundException("Property `${key.name}` not found, sources: ${allSources.map { it.getName().value }}")

        val keyValue = source.getValue(key.name)
        if (key is ObjectPropertyKey<T>) {
            if (isListWithElementType(keyValue, key.type)) {
                throw PropertyKeyTypeException("Property `${key.name}` is a list but was requested as object")
            }
            if (!isObjectOfType(keyValue, key.type)) {
                throw PropertyKeyTypeException("Property `${key.name}` is not object of type `${key.type.simpleName}`")
            }
            return getObjectWithType(keyValue, key.type)
        }
        if (key is ListPropertyKey<*>) {
            if (isObjectOfType(keyValue, key.elementType)) {
                throw PropertyKeyTypeException("Property `${key.name}` is an object but was requested as list")
            }
            if (!isListWithElementType(keyValue, key.elementType)) {
                throw PropertyKeyTypeException("Property `${key.name}` is not list with element type `${key.elementType.simpleName}`")
            }
            return getListWithElementType(keyValue, key.elementType) as T
        }

        throw ShouldNeverHappenException()
    }

    override fun <Id : Any, E : Any> findElement(key: MapPropertyKey<Id, E>, id: Id): E? {
        val list = get(key)
        return list.firstOrNull { key.idProvider(it) == id }
    }

    override fun addSource(source: PropertiesSource) {
        addedSources.add(source)
    }

    private fun findSourceWithKeyName(keyName: String): PropertiesSource? {
        return allSources.firstOrNull {
            it.getAllKeys().contains(keyName)
        }
    }

    private val objectMapper = ObjectMapper()
        .registerKotlinModule()

    private val serializer = SerializerLogic()

    private fun <T: Any> isListWithElementType(value: SerializedValue, type: KClass<T>): Boolean {
        return try {
            getListWithElementType(value, type)
            true
        } catch (e: Exception) {
            false
        }
    }



    private fun <T: Any> isObjectOfType(value: SerializedValue, type: KClass<T>): Boolean {
        return try {
            getObjectWithType(value, type)
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun <T: Any> getListWithElementType(value: SerializedValue, type: KClass<T>): List<T> {
        return serializer.deserializeList(value, type.java)
    }

    private fun <T: Any> getObjectWithType(value: SerializedValue, type: KClass<T>): T {
        return serializer.deserialize(value, type.java)
    }
}

package com.github.bratek20.architecture.properties.impl

import com.github.bratek20.architecture.exceptions.ShouldNeverHappenException
import com.github.bratek20.architecture.properties.api.*
import com.github.bratek20.architecture.serialization.api.DeserializationException
import com.github.bratek20.architecture.serialization.api.SerializedValue
import com.github.bratek20.architecture.serialization.impl.SerializerLogic
import kotlin.reflect.KClass


class PropertiesLogic(
    private val initialSources: Set<PropertiesSource>
) : Properties {
    private val addedSources = mutableListOf<PropertiesSource>()

    private val allSources: Set<PropertiesSource>
        get() = initialSources + addedSources

    override fun <T : Any> get(key: PropertyKey<T>): T {
        val source = findSourceWithKeyName(key.name)
            ?: throw PropertyNotFoundException("Property `${key.name}` not found, sources: ${allSources.map { it.getName().value }}")

        val keyValue = source.getValue(key.name)
        if (key is ObjectPropertyKey<T>) {
            if (isListWithElementType(keyValue, key.type) == null) {
                throw PropertyKeyTypeException("Property `${key.name}` is a list but was requested as object")
            }
            isObjectOfType(keyValue, key.type)?.let {
                throw PropertyKeyTypeException("Property `${key.name}` is not object of type `${key.type.simpleName}`: $it")
            }
            return getObjectWithType(keyValue, key.type)
        }
        if (key is ListPropertyKey<*>) {
            if (isObjectOfType(keyValue, key.elementType) == null) {
                throw PropertyKeyTypeException("Property `${key.name}` is an object but was requested as list")
            }
            isListWithElementType(keyValue, key.elementType)?.let {
                throw PropertyKeyTypeException("Property `${key.name}` is not list with element type `${key.elementType.simpleName}`: $it")
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

    private val serializer = SerializerLogic()

    private fun <T: Any> isListWithElementType(value: SerializedValue, type: KClass<T>): String? {
        return try {
            getListWithElementType(value, type)
            null
        } catch (e: DeserializationException) {
            extractCause(e)
        }
    }

    private fun <T: Any> isObjectOfType(value: SerializedValue, type: KClass<T>): String? {
        return try {
            getObjectWithType(value, type)
            null
        } catch (e: DeserializationException) {
            extractCause(e)
        }
    }

    private fun extractCause(e: DeserializationException): String {
        val cause = e.message!!.replace("Deserialization failed: ", "")
        return cause
    }

    private fun <T: Any> getListWithElementType(value: SerializedValue, type: KClass<T>): List<T> {
        return serializer.deserializeList(value, type.java)
    }

    private fun <T: Any> getObjectWithType(value: SerializedValue, type: KClass<T>): T {
        return serializer.deserialize(value, type.java)
    }
}

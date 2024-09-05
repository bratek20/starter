package com.github.bratek20.architecture.data.impl

import com.github.bratek20.architecture.data.api.*
import com.github.bratek20.architecture.exceptions.ShouldNeverHappenException
import com.github.bratek20.architecture.properties.api.*
import com.github.bratek20.architecture.serialization.api.DeserializationException
import com.github.bratek20.architecture.serialization.api.SerializedValue
import com.github.bratek20.architecture.serialization.impl.SerializerLogic
import kotlin.reflect.KClass

class DataStorageLogic(
    private val integration: DataStorageIntegration
) : DataStorage {
    override fun <T : Any> set(key: DataKey<T>, value: T) {
        integration.setValue(key.name, SerializerLogic().serialize(value))
    }

    override fun <T : Any> find(key: DataKey<T>): T? {
        TODO("Not yet implemented")
    }

    override fun <T : Any> get(key: DataKey<T>): T {
        val keyValue = integration.findValue(key.name)
        if (keyValue == null) {
            throw DataNotFoundException("Data `${key.name}` not found")
        }

        if (key is ObjectDataKey<T>) {
            if (isListWithElementType(keyValue, key.type) == null) {
                throw DataKeyTypeException("Data `${key.name}` is a list but was requested as object")
            }
            isObjectOfType(keyValue, key.type)?.let {
                throw DataKeyTypeException("Data `${key.name}` is not object of type `${key.type.simpleName}`: $it")
            }
            return getObjectWithType(keyValue, key.type)
        }
        if (key is ListDataKey<*>) {
            if (isObjectOfType(keyValue, key.elementType) == null) {
                throw DataKeyTypeException("Data `${key.name}` is an object but was requested as list")
            }
            isListWithElementType(keyValue, key.elementType)?.let {
                throw DataKeyTypeException("Data `${key.name}` is not list with element type `${key.elementType.simpleName}`: $it")
            }
            return getListWithElementType(keyValue, key.elementType) as T
        }

        throw ShouldNeverHappenException()
    }

    override fun <Id : Any, E : Any> addElement(key: MapDataKey<Id, E>, id: Id, value: E): Boolean {
        TODO("Not yet implemented")
    }

    override fun <Id : Any, E : Any> findElement(key: MapDataKey<Id, E>, id: Id): E? {
        val list = get(key)
        return list.firstOrNull { key.idProvider(it) == id }
    }

    override fun <Id : Any, E : Any> getElement(key: MapDataKey<Id, E>): E {
        TODO("Not yet implemented")
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

package com.github.bratek20.architecture.storage.impl

import com.github.bratek20.architecture.exceptions.ShouldNeverHappenException
import com.github.bratek20.architecture.serialization.api.DeserializationException
import com.github.bratek20.architecture.serialization.api.SerializedValue
import com.github.bratek20.architecture.serialization.impl.SerializerLogic
import com.github.bratek20.architecture.storage.api.*
import kotlin.reflect.KClass

abstract class StorageLogic {
    private val serializer = SerializerLogic()

    abstract fun findValue(keyName: String): SerializedValue?

    abstract fun storageElementName(): String

    abstract fun notFoundException(message: String): NotFoundInStorageException
    abstract fun keyTypeException(message: String): StorageKeyTypeException

    fun <T : Any> get(key: TypedKey<T>): T {
        val keyValue = findValue(key.name) ?: throw notFoundException("${storageElementName()} `${key.name}` not found")

        if (key is ObjectTypedKey<T>) {
            if (isListWithElementType(keyValue, key.type) == null) {
                throw keyTypeException("${storageElementName()} `${key.name}` is a list but was requested as object")
            }
            isObjectOfType(keyValue, key.type)?.let {
                throw keyTypeException("${storageElementName()} `${key.name}` is not object of type `${key.type.simpleName}`: $it")
            }
            return getObjectWithType(keyValue, key.type)
        }
        if (key is ListTypedKey<*>) {
            if (isObjectOfType(keyValue, key.elementType) == null) {
                throw keyTypeException("${storageElementName()} `${key.name}` is an object but was requested as list")
            }
            isListWithElementType(keyValue, key.elementType)?.let {
                throw keyTypeException("${storageElementName()} `${key.name}` is not list with element type `${key.elementType.simpleName}`: $it")
            }
            return getListWithElementType(keyValue, key.elementType) as T
        }

        throw ShouldNeverHappenException()
    }

    fun <Id : Any, E : Any> findElement(key: MapTypedKey<Id, E>, id: Id): E? {
        val list = get(key)
        return list.firstOrNull { key.idProvider(it) == id }
    }

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

    fun <T: Any> getListWithElementType(value: SerializedValue, type: KClass<T>): List<T> {
        return serializer.deserializeList(value, type.java)
    }

    fun <T: Any> getObjectWithType(value: SerializedValue, type: KClass<T>): T {
        return serializer.deserialize(value, type.java)
    }
}
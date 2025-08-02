package com.github.bratek20.architecture.storage.impl

import com.github.bratek20.architecture.exceptions.ShouldNeverHappenException
import com.github.bratek20.architecture.serialization.api.DeserializationException
import com.github.bratek20.architecture.serialization.api.SerializedValue
import com.github.bratek20.architecture.serialization.context.SerializationFactory
import com.github.bratek20.architecture.serialization.impl.SerializerLogic
import com.github.bratek20.architecture.storage.api.*
import kotlin.reflect.KClass

abstract class StorageLogic {
    abstract fun findValue(keyName: String): SerializedValue?
    abstract fun setValue(keyName: String, value: SerializedValue)

    abstract fun storageEntityName(): String

    abstract fun notFoundInStorageException(message: String): NotFoundInStorageException
    abstract fun elementNotFoundException(message: String): StorageElementNotFoundException
    abstract fun keyTypeException(message: String): StorageKeyTypeException

    fun <T : Any> find(key: TypedKey<T>): T? {
        findValue(key.name) ?: return null
        return get(key)
    }

    fun <T : Any> get(key: TypedKey<T>): T {
        val keyValue = findValue(key.name) ?: throw notFoundInStorageException("${storageEntityName()} `${key.name}` not found")

        if (key is ObjectTypedKey<T>) {
            if (isListWithElementType(keyValue, key.type) == null) {
                throw keyTypeException("${storageEntityName()} `${key.name}` is a list but was requested as object")
            }
            isObjectOfType(keyValue, key.type)?.let {
                throw keyTypeException("${storageEntityName()} `${key.name}` is not object of type `${key.type.simpleName}`: $it")
            }
            return getObjectWithType(keyValue, key.type)
        }
        if (key is ListTypedKey<*>) {
            if (isObjectOfType(keyValue, key.elementType) == null) {
                throw keyTypeException("${storageEntityName()} `${key.name}` is an object but was requested as list")
            }
            isListWithElementType(keyValue, key.elementType)?.let {
                throw keyTypeException("${storageEntityName()} `${key.name}` is not list with element type `${key.elementType.simpleName}`: $it")
            }
            return getListWithElementType(keyValue, key.elementType) as T
        }

        throw ShouldNeverHappenException()
    }

    fun <Id : Any, E : Any> findElement(key: MapTypedKey<Id, E>, id: Id): E? {
        val list = get(key)
        return list.firstOrNull { key.idProvider(it) == id }
    }

    fun <Id : Any, E : Any> getElement(key: MapTypedKey<Id, E>, id: Id): E {
        return findElement(key, id)
            ?: throw elementNotFoundException(
                "${storageEntityName()} element with id `$id` for key `${key.name}` not found"
            )
    }

    fun <Id : Any, E : Any> addElement(key: MapTypedKey<Id, E>, value: E): Boolean {
        val list = get(key).toMutableList()
        val id = key.idProvider(value)
        if (list.any { key.idProvider(it) == id }) {
            return false
        }

        list.add(value)
        setValue(key.name, serializer.serialize(list))
        return true
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

    private fun <T: Any> getListWithElementType(value: SerializedValue, type: KClass<T>): List<T> {
        return serializer.deserializeList(value, type.java)
    }

    private fun <T: Any> getObjectWithType(value: SerializedValue, type: KClass<T>): T {
        return serializer.deserialize(value, type.java)
    }

    companion object {
        val serializer = SerializationFactory.createSerializer()
    }
}
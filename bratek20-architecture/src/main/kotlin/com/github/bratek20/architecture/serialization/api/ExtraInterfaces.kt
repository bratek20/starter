package com.github.bratek20.architecture.serialization.api

import com.github.bratek20.architecture.exceptions.ApiException
import com.github.bratek20.architecture.structs.api.Struct
import com.github.bratek20.architecture.structs.api.SerializableList

class DeserializationException(message: String): ApiException(message)

interface Serializer {
    fun serialize(value: Any): SerializedValue

    fun asJson(value: Any): String {
        return serialize(value).getValue()
    }

    @Throws(DeserializationException::class)
    fun <T> deserialize(serializedValue: SerializedValue, type: Class<T>): T

    fun <T> fromJsonObject(value: String, type: Class<T>): T {
        return deserialize(SerializedValue.create(value, SerializationType.JSON), type)
    }

    @Throws(DeserializationException::class)
    fun <T> deserializeList(serializedValue: SerializedValue, elementType: Class<T>): List<T>

    fun <T> fromJsonList(value: String, elementType: Class<T>): List<T> {
        return deserializeList(SerializedValue.create(value, SerializationType.JSON), elementType)
    }

    fun asStruct(value: Any): Struct

    fun asStructList(value: Any): SerializableList

    @Throws(DeserializationException::class)
    fun <T> fromStruct(struct: Struct, type: Class<T>): T
}
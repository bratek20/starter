package com.github.bratek20.architecture.serialization.api

interface Serializer {
    fun serialize(value: Any): SerializedValue
    fun <T> deserialize(serializedValue: SerializedValue, type: Class<T>): T
}
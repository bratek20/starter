package com.github.bratek20.architecture.serialization.impl

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.core.JacksonException
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.github.bratek20.architecture.serialization.api.*

class SerializerLogic: Serializer {
    private val objectMapper: ObjectMapper = ObjectMapper()

    init {
        // Configure Jackson to include all fields and ignore getters
        objectMapper.setVisibility(
            objectMapper.serializationConfig
                .defaultVisibilityChecker
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withIsGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE)
        )
        objectMapper.registerKotlinModule()
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }

    override fun serialize(value: Any): SerializedValue {
        val jsonString = objectMapper.writeValueAsString(value)
        return SerializedValue.create(
            value = jsonString,
            type = SerializationType.JSON,
        )
    }

    override fun <T> deserialize(serializedValue: SerializedValue, type: Class<T>): T {
        try {
            return objectMapper.readValue(serializedValue.getValue(), type)
        } catch (e: JacksonException) {
            throw handleJacksonException(e)
        }
    }

    override fun <T> deserializeList(serializedValue: SerializedValue, elementType: Class<T>): List<T> {
        try {
            val collectionType = objectMapper.typeFactory.constructCollectionType(List::class.java, elementType)
            return objectMapper.readValue(serializedValue.getValue(), collectionType)
        } catch (e: JacksonException) {
            throw handleJacksonException(e)
        }
    }

    private fun handleJacksonException(e: JacksonException): DeserializationException {
        val internalMsg = e.message ?: ""
        extractMissingFieldName(internalMsg)?.let { missingFieldName ->
            return DeserializationException("Deserialization failed: missing value for field `$missingFieldName`")
        }
        return DeserializationException("Deserialization failed: unmapped message: $internalMsg")
    }

    private fun extractMissingFieldName(message: String): String? {
        val regex = "property ([a-zA-Z0-9]+) due to missing".toRegex()
        val matchResult = regex.find(message)
        return matchResult?.groups?.get(1)?.value
    }

    override fun asStruct(value: Any): Struct {
        val tmp = serialize(value)
        return deserialize(tmp, Struct::class.java)
    }

    override fun <T> fromStruct(struct: Struct, type: Class<T>): T {
        return deserialize(serialize(struct), type)
    }
}
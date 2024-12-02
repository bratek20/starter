package com.github.bratek20.architecture.serialization.impl

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.core.JacksonException
import com.fasterxml.jackson.core.util.DefaultIndenter
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.github.bratek20.architecture.serialization.api.*
import com.github.bratek20.architecture.structs.api.Struct

class SerializerLogic(
    private val config: SerializerConfig
): Serializer {
    private val mapper: ObjectMapper = ObjectMapper()

    init {
        // Configure Jackson to include all fields and ignore getters
        mapper.setVisibility(
            mapper.serializationConfig
                .defaultVisibilityChecker
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withIsGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE)
        )
        mapper.registerKotlinModule()
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true)
    }



    override fun serialize(value: Any): SerializedValue {
        val jsonString = if (config.getReadable()) {
            asReadableJson(value)
        } else {
            mapper.writeValueAsString(value)
        }

        return SerializedValue.create(
            value = jsonString,
            type = SerializationType.JSON,
        )
    }

    class CustomPrettyPrinter : DefaultPrettyPrinter() {
        init {
            _objectFieldValueSeparatorWithSpaces = ": "  // No space before colon
            val indenter = DefaultIndenter("  ", "\n")  // Two spaces for indentation, LF as newline
            indentArraysWith(indenter)
            indentObjectsWith(indenter)
        }

        override fun createInstance(): CustomPrettyPrinter {
            return CustomPrettyPrinter()
        }
    }
    private fun asReadableJson(value: Any): String {
        val prettyPrinter = CustomPrettyPrinter()
        return mapper.writer(prettyPrinter).writeValueAsString(value)
    }

    override fun <T> deserialize(serializedValue: SerializedValue, type: Class<T>): T {
        try {
            return mapper.readValue(serializedValue.getValue(), type)
        } catch (e: JacksonException) {
            throw handleJacksonException(e)
        }
    }

    override fun <T> deserializeList(serializedValue: SerializedValue, elementType: Class<T>): List<T> {
        try {
            val collectionType = mapper.typeFactory.constructCollectionType(List::class.java, elementType)
            return mapper.readValue(serializedValue.getValue(), collectionType)
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
        matchResult?.groups?.get(1)?.value?.let { missingFieldName ->
            return missingFieldName
        }

        //Missing required creator property 'number' (index 1)
        val otherRegex = "Missing required creator property '([a-zA-Z0-9]+)'".toRegex()
        val otherMatchResult = otherRegex.find(message)
        otherMatchResult?.groups?.get(1)?.value?.let { missingFieldName ->
            return missingFieldName
        }

        return null
    }

    override fun asStruct(value: Any): Struct {
        val tmp = serialize(value)
        return deserialize(tmp, Struct::class.java)
    }

    override fun <T> fromStruct(struct: Struct, type: Class<T>): T {
        return deserialize(serialize(struct), type)
    }
}
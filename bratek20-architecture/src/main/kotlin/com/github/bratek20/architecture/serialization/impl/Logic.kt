package com.github.bratek20.architecture.serialization.impl

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.databind.ObjectMapper
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
    }

    override fun serialize(value: Any): SerializedValue {
        val jsonString = objectMapper.writeValueAsString(value)
        return SerializedValue.create(
            value = jsonString,
            type = SerializationType.JSON,
        )
    }
}
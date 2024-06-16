package pl.bratek20.architecture.serialization.impl

import pl.bratek20.architecture.serialization.api.*

class SerializerLogic: Serializer {
    override fun serialize(value: Any): SerializedValue {
        return SerializedValue.create(
            value = "{\"value\":\"test\",\"number\":1}",
            type = SerializationType.JSON,
        )
    }
}
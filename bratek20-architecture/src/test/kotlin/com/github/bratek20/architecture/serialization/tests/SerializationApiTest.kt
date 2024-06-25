package com.github.bratek20.architecture.serialization.tests

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.github.bratek20.architecture.context.someContextBuilder
import com.github.bratek20.architecture.exceptions.assertApiExceptionThrown
import com.github.bratek20.architecture.serialization.api.*
import com.github.bratek20.architecture.serialization.context.SerializationImpl
import com.github.bratek20.architecture.serialization.fixtures.assertSerializedValue
import com.github.bratek20.architecture.serialization.fixtures.serializedValue
import org.assertj.core.api.Assertions.assertThat

class SerializationApiTest {
    data class TestObject(
        val value: String,
        val number: Int,
    )

    private lateinit var serializer: Serializer
    @BeforeEach
    fun setUp() {
        serializer = someContextBuilder()
            .withModules(SerializationImpl())
            .get(Serializer::class.java)
    }

    @Test
    fun `should serialize as JSON`() {
        val testObject = TestObject("test", 1)

        val serializedValue = serializer.serialize(testObject)

        assertSerializedValue(serializedValue) {
            value = "{\"value\":\"test\",\"number\":1}"
            type = SerializationType.JSON
        }
    }

    @Test
    fun `should deserialize from JSON`() {
        val obj = TestObject("test", 1)
        val serializedValue = serializer.serialize(obj)

        val deserializedObject = serializer.deserialize(serializedValue, TestObject::class.java)

        assertThat(deserializedObject).isEqualTo(obj)
    }

    @Test
    fun `should throw exception when deserializing from invalid JSON`() {
        val serializedValue = serializedValue {
            value = "{}"
        }

        assertApiExceptionThrown (
            { serializer.deserialize(serializedValue, TestObject::class.java) },
            {
                type = DeserializationException::class
                messagePrefix = "Failed to deserialize value"
            }
        )
    }

    @Test
    fun `should deserialize to Dictionary type`() {
        val obj = TestObject("test", 1)
        val serializedValue = serializer.serialize(obj)

        val deserializedObject = serializer.deserialize(serializedValue, Dictionary::class.java)

        assertThat(deserializedObject).isEqualTo(mapOf("value" to "test", "number" to 1))
    }

    @Test
    fun `should serialize Dictionary`() {
        val dictionary = DictionaryBuilder()
            .add("value", "test")
            .add("number", 1)
            .build()

        val serializedValue = serializer.serialize(dictionary)

        assertSerializedValue(serializedValue) {
            value = "{\"number\":1,\"value\":\"test\"}"
            type = SerializationType.JSON
        }
    }

    data class SomeId(
        val value: String,
    )

    data class PrivateFieldsWithGetters(
        private val id: String,
    ) {
        fun getId(): SomeId {
            return SomeId(id)
        }
    }

    @Test
    fun `should serialize private fields without getters`() {
        val obj = PrivateFieldsWithGetters("test")

        val serializedValue = serializer.serialize(obj)

        assertSerializedValue(serializedValue) {
            value = "{\"id\":\"test\"}"
        }
    }

    data class OnlyResultField(
        val result: String,
    )
    @Test
    fun `should not throw exception for missing fields`() {
        val json = "{\"error\":null,\"result\":\"OK\"}" //error field not in the class

        val deserializedObject = serializer.deserialize(serializedValue {
            value = json
            type = SerializationType.JSON
        }, OnlyResultField::class.java)

        assertThat(deserializedObject).isEqualTo(OnlyResultField("OK"))
    }
}
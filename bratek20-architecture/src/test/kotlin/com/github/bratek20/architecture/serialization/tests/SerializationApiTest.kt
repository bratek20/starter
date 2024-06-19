package com.github.bratek20.architecture.serialization.tests

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.github.bratek20.architecture.context.someContextBuilder
import com.github.bratek20.architecture.serialization.api.SerializationType
import com.github.bratek20.architecture.serialization.api.Serializer
import com.github.bratek20.architecture.serialization.context.SerializationImpl
import com.github.bratek20.architecture.serialization.fixtures.assertSerializedValue

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
}
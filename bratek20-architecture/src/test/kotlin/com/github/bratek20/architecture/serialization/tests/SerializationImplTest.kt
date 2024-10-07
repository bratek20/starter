package com.github.bratek20.architecture.serialization.tests

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.github.bratek20.architecture.context.someContextBuilder
import com.github.bratek20.architecture.context.stableContextBuilder
import com.github.bratek20.architecture.exceptions.assertApiExceptionThrown
import com.github.bratek20.architecture.serialization.api.*
import com.github.bratek20.architecture.serialization.context.SerializationFactory
import com.github.bratek20.architecture.serialization.context.SerializationImpl
import com.github.bratek20.architecture.serialization.fixtures.assertStructEquals
import com.github.bratek20.architecture.serialization.fixtures.assertSerializedValue
import com.github.bratek20.architecture.serialization.fixtures.serializedValue
import com.github.bratek20.architecture.serialization.fixtures.serializerConfig
import org.assertj.core.api.Assertions.assertThat

class SerializationImplTest {
    data class SomeValue(
        val value: String
    )

    data class TestObject(
        private val value: String,
        private val number: Int,
        private val nullable: String?
    ) {
        fun getValue(): SomeValue {
            return SomeValue(this.value)
        }

        fun getNumber(): Int {
            return this.number
        }

        fun getNullable(): String? {
            return this.nullable
        }

        companion object {
            fun create(
                value: SomeValue,
                number: Int,
                nullable: String?
            ): TestObject {
                return TestObject(
                    value = value.value,
                    number = number,
                    nullable = nullable
                )
            }
        }
    }

    private lateinit var serializer: Serializer
    @BeforeEach
    fun setUp() {
        serializer = stableContextBuilder()
            .withModules(SerializationImpl())
            .get(Serializer::class.java)
    }

    @Test
    fun `should serialize as JSON`() {
        val testObject = TestObject("test", 1, null)

        val serializedValue = serializer.serialize(testObject)

        assertSerializedValue(serializedValue) {
            value = "{\"value\":\"test\",\"number\":1,\"nullable\":null}"
            type = "JSON"
        }
    }

    @Test
    fun `should serialize as formatted JSON`() {
        val configuredSerializer = SerializationFactory.createSerializer(
            serializerConfig {
                readable = true
            }
        )

        val testObject = TestObject("test", 1, null)

        val serializedValue = serializer.serialize(testObject)

        assertSerializedValue(serializedValue) {
            value = """
                {
                  "value": "test",
                  "number": 1,
                  "nullable": null
                }
            """.trimIndent()
            type = "JSON"
        }
    }

    @Test
    fun `should deserialize object from JSON`() {
        val serializedValue = serializedValue {
            value = "{\"value\":\"test\",\"number\":1}"
        }

        val deserializedObject = serializer.deserialize(serializedValue, TestObject::class.java)

        assertThat(deserializedObject).isEqualTo(TestObject("test", 1, null))
    }

    @Test
    fun `should deserialize list from JSON`() {
        val serializedValue = serializedValue {
            value = "[{\"value\":\"test\",\"number\":1}]"
        }

        val deserializedObject = serializer.deserializeList(serializedValue, TestObject::class.java)

        assertThat(deserializedObject).isEqualTo(listOf(TestObject("test", 1, null)))
    }

    @Test
    fun `should make struct from object`() {
        val testObject = TestObject("test", 1, null)

        val struct = serializer.asStruct(testObject)

        assertStructEquals(struct) {
            "value" to "test"
            "number" to 1
            "nullable" to null
        }
    }

    @Test
    fun `should make object from struct`() {
        val struct = struct {
            "value" to "test"
            "number" to 1
        }

        val obj = serializer.fromStruct(struct, TestObject::class.java)

        assertThat(obj).isEqualTo(TestObject("test", 1, null))
    }

    data class NestedObject(
        val nested: TestObject,
        val value: String
    )
    @Test
    fun `should make nested object from struct`() {
        val struct = struct {
            "nested" to struct {
                "value" to "test"
                "number" to 1
            }
            "value" to "xd"
        }

        val obj = serializer.fromStruct(struct, NestedObject::class.java)

        assertThat(obj)
            .isEqualTo(NestedObject(
                TestObject("test", 1, null),
                "xd"
            ))
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
                message = "Deserialization failed: missing value for field `value`"
            }
        )
    }

    @Test
    fun `should throw exception when deserializing list from invalid JSON`() {
        val serializedValue = serializedValue {
            value = "[{}]"
        }

        assertApiExceptionThrown (
            { serializer.deserializeList(serializedValue, TestObject::class.java) },
            {
                type = DeserializationException::class
                message = "Deserialization failed: missing value for field `value`"
            }
        )
    }

    @Test
    fun `should throw exception when deserializing from invalid struct`() {
        val struct = StructBuilder()
            .build()

        assertApiExceptionThrown (
            { serializer.fromStruct(struct, TestObject::class.java) },
            {
                type = DeserializationException::class
                message = "Deserialization failed: missing value for field `value`"
            }
        )
    }

    @Test
    fun `should deserialize to Struct type`() {
        val serializedValue = serializedValue {
            value = "{\"value\":\"test\",\"number\":1}"
        }

        val deserializedObject = serializer.deserialize(serializedValue, Struct::class.java)

        val expected = mapOf("value" to "test", "number" to 1)
        assertThat(deserializedObject).isEqualTo(expected)
    }

    @Test
    fun `should serialize Struct`() {
        val struct = struct {
            "value" to "test"
            "number" to 1
        }

        val serializedValue = serializer.serialize(struct)

        assertSerializedValue(serializedValue) {
            value = "{\"number\":1,\"value\":\"test\"}"
            type = "JSON"
        }
    }

    @Test
    fun `should serialize StructList`() {
        val structList = structList {
            struct {
                "value" to "test"
                "number" to 1
            }
            struct {
                "value" to "test2"
                "number" to 2
            }
        }

        val serializedValue = serializer.serialize(structList)

        assertSerializedValue(serializedValue) {
            value = "[{\"number\":1,\"value\":\"test\"},{\"number\":2,\"value\":\"test2\"}]"
            type = "JSON"
        }
    }

    @Test
    fun `should deserialize from StructList`() {
        val structList = structList {
            struct {
                "value" to "test"
                "number" to 1
            }
            struct {
                "value" to "test2"
                "number" to 2
            }
        }
        val serializedValue = serializer.serialize(structList)

        val deserializedObject = serializer.deserialize(serializedValue, StructList::class.java)

        val expected = listOf(
            mapOf("value" to "test", "number" to 1),
            mapOf("value" to "test2", "number" to 2)
        )
        assertThat(deserializedObject).isEqualTo(expected)
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
            type = "JSON"
        }, OnlyResultField::class.java)

        assertThat(deserializedObject).isEqualTo(OnlyResultField("OK"))
    }

    @Test
    fun `should not throw exception for missing fields in struct`() {
        val struct = struct {
            "error" to null
            "result" to "OK"
        }

        val deserializedObject = serializer.fromStruct(struct, OnlyResultField::class.java)

        assertThat(deserializedObject).isEqualTo(OnlyResultField("OK"))
    }
}
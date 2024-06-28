package com.github.bratek20.architecture.serialization.tests

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.github.bratek20.architecture.context.someContextBuilder
import com.github.bratek20.architecture.exceptions.assertApiExceptionThrown
import com.github.bratek20.architecture.serialization.api.*
import com.github.bratek20.architecture.serialization.context.SerializationImpl
import com.github.bratek20.architecture.serialization.fixtures.assertDictionary
import com.github.bratek20.architecture.serialization.fixtures.assertSerializedValue
import com.github.bratek20.architecture.serialization.fixtures.serializedValue
import org.assertj.core.api.Assertions.assertThat

class SerializationApiTest {
    data class TestObject(
        val value: String,
        val number: Int,
        val nullable: String?
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
        val testObject = TestObject("test", 1, null)

        val serializedValue = serializer.serialize(testObject)

        assertSerializedValue(serializedValue) {
            value = "{\"value\":\"test\",\"number\":1,\"nullable\":null}"
            type = SerializationType.JSON
        }
    }

    @Test
    fun `should deserialize from JSON`() {
        val serializedValue = serializedValue {
            value = "{\"value\":\"test\",\"number\":1}"
        }

        val deserializedObject = serializer.deserialize(serializedValue, TestObject::class.java)

        assertThat(deserializedObject).isEqualTo(TestObject("test", 1, null))
    }

    @Test
    fun `should make dictionary from object`() {
        val testObject = TestObject("test", 1, null)

        val dict = serializer.asDictionary(testObject)

        assertDictionary(dict, mapOf(
            "value" to "test",
            "number" to 1,
            "nullable" to null
        ))
    }

    @Test
    fun `should make object from dictionary`() {
        val dict = dictionary {
            "value" to "test"
            "number" to 1
        }

        val obj = serializer.fromDictionary(dict, TestObject::class.java)

        assertThat(obj).isEqualTo(TestObject("test", 1, null))
    }

    data class NestedObject(
        val nested: TestObject,
        val value: String
    )
    @Test
    fun `should make nested object from dictionary`() {
        val dict = dictionary {
            "nested" to dictionary {
                "value" to "test"
                "number" to 1
            }
            "value" to "xd"
        }

        val obj = serializer.fromDictionary(dict, NestedObject::class.java)

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
                message = "Failed to deserialize value, missing value for field: value"
            }
        )
    }

    @Test
    fun `should throw exception when deserializing from invalid dictionary`() {
        val dict = DictionaryBuilder()
            .build()

        assertApiExceptionThrown (
            { serializer.fromDictionary(dict, TestObject::class.java) },
            {
                type = DeserializationException::class
                message = "Failed to deserialize value, missing value for field: value"
            }
        )
    }

    @Test
    fun `should deserialize to Dictionary type`() {
        val serializedValue = serializedValue {
            value = "{\"value\":\"test\",\"number\":1}"
        }

        val deserializedObject = serializer.deserialize(serializedValue, Dictionary::class.java)

        val expected = mapOf("value" to "test", "number" to 1)
        assertThat(deserializedObject).isEqualTo(expected)
    }

    @Test
    fun `should serialize Dictionary`() {
        val dict = dictionary {
            "value" to "test"
            "number" to 1
        }

        val serializedValue = serializer.serialize(dict)

        assertSerializedValue(serializedValue) {
            value = "{\"number\":1,\"value\":\"test\"}"
            type = SerializationType.JSON
        }
    }

    @Test
    fun `should serialize DictionaryList`() {
        val dictList = dictionaryList {
            dictionary {
                "value" to "test"
                "number" to 1
            }
            dictionary {
                "value" to "test2"
                "number" to 2
            }
        }

        val serializedValue = serializer.serialize(dictList)

        assertSerializedValue(serializedValue) {
            value = "[{\"number\":1,\"value\":\"test\"},{\"number\":2,\"value\":\"test2\"}]"
            type = SerializationType.JSON
        }
    }

    @Test
    fun `should deserialize from DictionaryList`() {
        val dictList = dictionaryList {
            dictionary {
                "value" to "test"
                "number" to 1
            }
            dictionary {
                "value" to "test2"
                "number" to 2
            }
        }
        val serializedValue = serializer.serialize(dictList)

        val deserializedObject = serializer.deserialize(serializedValue, DictionaryList::class.java)

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
            type = SerializationType.JSON
        }, OnlyResultField::class.java)

        assertThat(deserializedObject).isEqualTo(OnlyResultField("OK"))
    }

    @Test
    fun `should not throw exception for missing fields in dictionary`() {
        val dict = dictionary {
            "error" to null
            "result" to "OK"
        }

        val deserializedObject = serializer.fromDictionary(dict, OnlyResultField::class.java)

        assertThat(deserializedObject).isEqualTo(OnlyResultField("OK"))
    }
}
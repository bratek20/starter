package pl.bratek20.architecture.serialization.tests

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import pl.bratek20.architecture.context.someContextBuilder
import pl.bratek20.architecture.serialization.api.SerializationType
import pl.bratek20.architecture.serialization.api.Serializer
import pl.bratek20.architecture.serialization.context.SerializationImpl
import pl.bratek20.architecture.serialization.fixtures.assertSerializedValue

class SerializationApiTest {
    data class TestObject(
        val value: String,
        val number: Int,
    )

    @Test
    fun `should serialize`() {
        val testObject = TestObject("test", 1)

        val serializer = someContextBuilder()
            .withModules(SerializationImpl())
            .get(Serializer::class.java);

        val serializedValue = serializer.serialize(testObject)

        assertSerializedValue(serializedValue) {
            value = "{\"value\":\"test\",\"number\":1}"
            type = SerializationType.JSON
        }
    }
}
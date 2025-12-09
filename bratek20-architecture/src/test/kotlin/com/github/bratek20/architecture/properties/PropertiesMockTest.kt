package com.github.bratek20.architecture.properties

import com.github.bratek20.architecture.context.guice.GuiceContextBuilder
import com.github.bratek20.architecture.context.someContextBuilder
import com.github.bratek20.architecture.context.stableContextBuilder
import com.github.bratek20.architecture.exceptions.assertApiExceptionThrown
import com.github.bratek20.architecture.properties.api.ListPropertyKey
import com.github.bratek20.architecture.properties.api.ObjectPropertyKey
import com.github.bratek20.architecture.properties.api.Properties
import com.github.bratek20.architecture.structs.fixtures.assertStructEquals
import com.github.bratek20.architecture.structs.fixtures.assertStructListEquals
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PropertiesMockTest {
    class SomeClass(val value: String)
    companion object {
        val OBJECT_KEY = ObjectPropertyKey("key1", SomeClass::class)
        val LIST_KEY = ListPropertyKey("key2", SomeClass::class)
    }

    private lateinit var properties: Properties
    private lateinit var propertiesMock: PropertiesMock

    @BeforeEach
    fun setup() {
        val c = stableContextBuilder()
            .withModules(PropertiesMocks())
            .build()

        properties = c.get(Properties::class.java)
        propertiesMock = c.get(PropertiesMock::class.java)
    }

    @Test
    fun `should work for object keys`() {
        assertApiExceptionThrown(
            { properties.get(OBJECT_KEY) },
            {
                type = ObjectPropertyNotSetException::class
                message = "No value was set in mock for object key `key1`"
            }
        )

        
        propertiesMock.set(OBJECT_KEY, SomeClass("value"))
        val value = properties.get(OBJECT_KEY)
        assertThat(value.value).isEqualTo("value")

        // re-set to different value
        propertiesMock.set(OBJECT_KEY, SomeClass("value2"))
        val value2 = properties.get(OBJECT_KEY)
        assertThat(value2.value).isEqualTo("value2")
    }

    @Test
    fun `should work for different keys instances`() {
        propertiesMock.set(ObjectPropertyKey("key1", SomeClass::class), SomeClass("value"))
        val value = properties.get(ObjectPropertyKey("key1", SomeClass::class))
        assertThat(value.value).isEqualTo("value")

        propertiesMock.set(ListPropertyKey("key2", SomeClass::class), listOf(SomeClass("y")))
        val value2 = properties.get(ListPropertyKey("key2", SomeClass::class))
        assertThat(value2).hasSize(1)
    }

    @Test
    fun `should work for list keys`() {
        //does not throw
        assertThat(properties.get(LIST_KEY)).isEmpty()

        //but find returns null
        assertThat(properties.find(LIST_KEY)).isNull()
    }

    @Test
    fun `should return getAll correctly`() {
        propertiesMock.set(OBJECT_KEY, SomeClass("x"))
        propertiesMock.set(LIST_KEY, listOf(SomeClass("y")))

        val allProperties = properties.getAll()

        assertThat(allProperties).hasSize(2)

        assertThat(allProperties[0].keyName).isEqualTo("key1")
        assertStructEquals(allProperties[0].value.asObject()) {
            "value" to "x"
        }

        assertThat(allProperties[1].keyName).isEqualTo("key2")
        assertStructListEquals(allProperties[1].value.asList(), listOf {
            "value" to "y"
        })
    }
}
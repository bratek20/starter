package com.github.bratek20.architecture.properties

import com.github.bratek20.architecture.exceptions.assertApiExceptionThrown
import com.github.bratek20.architecture.properties.api.ListPropertyKey
import com.github.bratek20.architecture.properties.api.ObjectPropertyKey
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PropertiesMockTest {
    class SomeClass(val value: String)

    private lateinit var propertiesMock: PropertiesMock

    @BeforeEach
    fun setup() {
        propertiesMock = PropertiesMock()
    }

    @Test
    fun `should work for object keys`() {
        val key = ObjectPropertyKey("key", SomeClass::class)

        assertApiExceptionThrown(
            { propertiesMock.get(key) },
            {
                type = ObjectPropertyNotSetException::class
                message = "No value was set in mock for object key `key`"
            }
        )

        
        propertiesMock.set(key, SomeClass("value"))
        val value = propertiesMock.get(key)
        assertThat(value.value).isEqualTo("value")

        // re-set to different value
        propertiesMock.set(key, SomeClass("value2"))
        val value2 = propertiesMock.get(key)
        assertThat(value2.value).isEqualTo("value2")
    }

    @Test
    fun `should work for list keys`() {
        val key = ListPropertyKey("key", SomeClass::class)

        assertThat(propertiesMock.get(key)).isEmpty()
    }
}
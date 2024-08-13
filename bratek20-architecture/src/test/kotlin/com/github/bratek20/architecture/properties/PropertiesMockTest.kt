package com.github.bratek20.architecture.properties

import com.github.bratek20.architecture.properties.api.ObjectPropertyKey
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PropertiesMockTest {
    class SomeClass(val value: String)

    @Test
    fun shouldWork() {
        val propertiesMock = PropertiesMock()
        val key = ObjectPropertyKey("key", SomeClass::class)
        propertiesMock.set(key, SomeClass("value"))

        val value = propertiesMock.get(key)
        assertThat(value.value).isEqualTo("value")
    }
}
package com.github.bratek20.architecture.data

import com.github.bratek20.architecture.properties.api.ObjectPropertyKey
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class DataMockTest {
    class SomeClass(val value: String)

    @Test
    fun shouldWork() {
        val propertiesMock = PropertiesMock()
        val key = ObjectPropertyKey("key", SomeClass::class)
        
        propertiesMock.set(key, SomeClass("value"))
        val value = propertiesMock.get(key)
        assertThat(value.value).isEqualTo("value")

        // re-set to different value
        propertiesMock.set(key, SomeClass("value2"))
        val value2 = propertiesMock.get(key)
        assertThat(value2.value).isEqualTo("value2")
    }
}
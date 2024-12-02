package com.github.bratek20.architecture.serialization.tests

import com.github.bratek20.architecture.serialization.fixtures.asStruct
import com.github.bratek20.architecture.structs.fixtures.assertStructContains
import com.github.bratek20.architecture.structs.fixtures.assertStructEquals
import org.junit.jupiter.api.Test

class SomeClass(val value: String, val otherValue: Int)

class SerializationFixturesTest {
    @Test
    fun `struct operations`() {
        val someClassStruct = asStruct(SomeClass("someValue", 1))

        assertStructContains(someClassStruct) {
            "value" to "someValue"
        }

        assertStructEquals(someClassStruct) {
            "value" to "someValue"
            "otherValue" to 1
        }
    }
}
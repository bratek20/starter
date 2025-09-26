package com.github.bratek20.architecture.structs.tests

import com.github.bratek20.architecture.serialization.fixtures.asStruct
import com.github.bratek20.architecture.structs.api.struct
import com.github.bratek20.architecture.structs.fixtures.assertStructContains
import com.github.bratek20.architecture.structs.fixtures.assertStructEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class SomeClass(val value: String, val nestedStructure: Map<String, Any>)

class StructsFixturesTest {
    @Test
    fun `struct operations`() {
        val someClassStruct = asStruct(
                SomeClass("someValue", mapOf("someMap" to mapOf("nestedValue" to 1)))
        )

        assertStructContains(someClassStruct) {
            "value" to "someValue"
        }

        assertStructContains(someClassStruct) {
            "nestedStructure" to struct {
                "someMap" to struct {
                    "nestedValue" to 1
                }
            }
        }

        assertStructEquals(someClassStruct) {
            "value" to "someValue"
            "nestedStructure" to struct {
                "someMap" to struct {
                    "nestedValue" to 1
                }
            }
        }
    }

    @Test
    @Disabled("Disabled as it should fail - ensuring assertions fails for value diff in nested value")
    fun `failing struct assertion by value`() {
        assertStructContains(
                struct {
                    "nestedStructure" to struct {
                        "someMap" to struct {
                            "nestedValue" to 1
                        }
                    }
                }
        ) {
            "nestedStructure" to struct {
                "someMap" to struct {
                    "nestedValue" to 2
                }
            }
        }
    }

    @Test
    @Disabled("Disabled as it should fail - ensuring assertions fails for type diff in nested value")
    fun `failing struct assertion by type`() {
        val someClassStruct = asStruct(SomeClass("someValue", mapOf("someMap" to 1)))
        assertStructContains(someClassStruct) {
            "value" to struct {
                "nestedValue" to 1
            }
        }
    }
}
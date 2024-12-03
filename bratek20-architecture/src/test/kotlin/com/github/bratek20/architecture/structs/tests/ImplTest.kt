package com.github.bratek20.architecture.structs.tests

import com.github.bratek20.architecture.exceptions.assertApiExceptionThrown
import com.github.bratek20.architecture.structs.api.*
import com.github.bratek20.architecture.structs.context.StructsFactory
import com.github.bratek20.architecture.structs.fixtures.structPath
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class StructsImplTest {
    @Test
    fun `should build with builders`() {
        struct {
            "key" to "value"
        }.let { struct ->
            assertThat(struct["key"]).isEqualTo("value")
        }

        structList {
            struct {
                "key" to "value"
            }
        }.let { structList ->
            assertThat(structList[0]["key"]).isEqualTo("value")
        }
    }

    @Test
    fun `should support AnyStruct operations`() {
        val anyStructObject: AnyStruct = struct {
            "key" to "value"
        }
        val anyStructList: AnyStruct = structList {
            struct {
                "key" to "value"
            }
        }

        assertThat(anyStructObject.isObject()).isTrue()
        assertThat(anyStructObject.isList()).isFalse()

        assertThat(anyStructList.isObject()).isFalse()
        assertThat(anyStructList.isList()).isTrue()

        assertDoesNotThrow {
            anyStructObject.asObject()
        }
        assertDoesNotThrow {
            anyStructList.asList()
        }

        assertApiExceptionThrown(
            anyStructObject::asList
        ) {
            type = StructConversionException::class
            message = "Tried to convert Struct to StructList"
        }

        assertApiExceptionThrown(
            anyStructList::asObject
        ) {
            type = StructConversionException::class
            message = "Tried to convert StructList to Struct"
        }
    }

    @Nested
    inner class AnyStructHelperScope {
        private lateinit var helper: AnyStructHelper

        @BeforeEach
        fun setup() {
            helper = StructsFactory.createAnyStructHelper()
        }

        @Test
        fun `should work`() {
            val assertValues = { anyStruct: AnyStruct, path: String, expectedValues: List<String> ->
                val values = helper.getValues(anyStruct, structPath(path))
                val rawValues = values.map { it.value }
                assertThat(rawValues).containsExactlyElementsOf(expectedValues)
            }

            val simpleObject = struct {
                "key" to "value"
            }
            assertValues(simpleObject, "key", listOf("value"))

            val simpleList = structList {
                struct {
                    "key" to "value1"
                }
                struct {
                    "key" to "value2"
                }
            }
            assertValues(simpleList, "[0]/key", listOf("value1"))
            assertValues(simpleList, "[1]/key", listOf("value2"))
            assertValues(simpleList, "[*]/key", listOf("value1", "value2"))
        }
    }
}
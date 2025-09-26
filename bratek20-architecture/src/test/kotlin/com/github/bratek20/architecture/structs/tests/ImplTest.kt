package com.github.bratek20.architecture.structs.tests

import com.github.bratek20.architecture.exceptions.assertApiExceptionThrown
import com.github.bratek20.architecture.serialization.context.SerializationFactory
import com.github.bratek20.architecture.structs.api.*
import com.github.bratek20.architecture.structs.context.StructsFactory
import com.github.bratek20.architecture.structs.fixtures.ExpectedStruct
import com.github.bratek20.architecture.structs.fixtures.assertStructEquals
import com.github.bratek20.architecture.structs.fixtures.structPath
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

private data class ExampleEntry(
    val entryValue: Int
)

private data class ExampleClass(
    val classValue: String,
    val entries: List<ExampleEntry>
)

class StructsImplTest {
    @Test
    fun `should build with builders`() {
        struct {
            "key" to "value"
        }.let { struct ->
            assertThat(struct["key"]).isEqualTo("value")
        }

        structList(
            {
                "key" to "value"
            }
        ).let { structList ->
            assertThat(structList[0]["key"]).isEqualTo("value")
        }
    }

    @Test
    fun `should support AnyStruct operations`() {
        val anyStructObject: AnyStruct = struct {
            "key" to "value"
        }
        val anyStructList: AnyStruct = structList (
            {
                "key" to "value"
            }
        ).asSerializableList()
        val anyStructPrimitive: AnyStruct = StructPrimitive("value")

        assertThat(anyStructObject.isObject()).isTrue()
        assertThat(anyStructObject.isList()).isFalse()
        assertThat(anyStructObject.isPrimitive()).isFalse()

        assertThat(anyStructList.isObject()).isFalse()
        assertThat(anyStructList.isList()).isTrue()
        assertThat(anyStructList.isPrimitive()).isFalse()

        assertThat(anyStructPrimitive.isObject()).isFalse()
        assertThat(anyStructPrimitive.isList()).isFalse()
        assertThat(anyStructPrimitive.isPrimitive()).isTrue()

        assertDoesNotThrow {
            anyStructObject.asObject()
        }
        assertDoesNotThrow {
            anyStructList.asList()
        }
        assertDoesNotThrow {
            anyStructPrimitive.asPrimitive()
        }

        assertApiExceptionThrown(
            anyStructObject::asList
        ) {
            type = StructConversionException::class
            message = "Tried to convert Struct to StructList"
        }
        assertApiExceptionThrown(
            anyStructObject::asPrimitive
        ) {
            type = StructConversionException::class
            message = "Tried to convert Struct to StructPrimitive"
        }

        assertApiExceptionThrown(
            anyStructList::asObject
        ) {
            type = StructConversionException::class
            message = "Tried to convert StructList to Struct"
        }
        assertApiExceptionThrown(
            anyStructList::asPrimitive
        ) {
            type = StructConversionException::class
            message = "Tried to convert StructList to StructPrimitive"
        }

        assertApiExceptionThrown(
            anyStructPrimitive::asObject
        ) {
            type = StructConversionException::class
            message = "Tried to convert StructPrimitive to Struct"
        }
        assertApiExceptionThrown(
            anyStructPrimitive::asList
        ) {
            type = StructConversionException::class
            message = "Tried to convert StructPrimitive to StructList"
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
        fun `simple object`() {
            val simpleObject = struct {
                "key" to "value"
            }

            assertPrimitiveValues(simpleObject, "key", listOf("value"))

            assertStructValues(simpleObject, "", listOf {
                "key" to "value"
            })
        }

        @Test
        fun `simple list`() {
            val simpleList = structList (
                {
                    "key" to "value1"
                },
                {
                    "key" to "value2"
                }
            ).asSerializableList()

            assertPrimitiveValues(simpleList, "[0]/key", listOf("value1"))
            assertPrimitiveValues(simpleList, "[1]/key", listOf("value2"))
            assertPrimitiveValues(simpleList, "[*]/key", listOf("value1", "value2"))

            assertStructValues(simpleList, "[*]", listOf(
                {
                    "key" to "value1"
                },
                {
                    "key" to "value2"
                }
            ))
        }

        @Test
        fun `nested object`() {
            val obj = struct {
                "key" to struct {
                    "nestedKey" to "value"
                }
            }

            assertPrimitiveValues(obj, "key/nestedKey", listOf("value"))

            assertStructValues(obj, "key", listOf {
                "nestedKey" to "value"
            })
        }

        @Test
        fun `BUG-TOBEFIXED test`() {
            val obj = struct {
                "someNestedWithUniqueIds" to listOf(
                    "otherClass" to struct {
                        "uniqueId" to "1"
                    }
                )
            }

            assertThat(obj).hasSize(2)
            assertThat(obj.getValue("someNestedWithUniqueIds")).isNotNull()
            assertThat(obj.getValue("otherClass")).isNotNull() // It not should be here but only inside someNestedWithUniqueIds
        }

        @Test
        fun `should throw traversal exception if null value detected`() {
            val obj = struct {
                "a" to struct {
                    "b" to null
                }
            }

            assertApiExceptionThrown(
                { helper.getValues(obj, structPath("a/b/c")) }
            ) {
                type = StructTraversalException::class
                message = "Null detected at 'a/b/'"
            }
        }

        @Test
        fun `should skip nullable fields only if are null`() {
            val s = structList(
                {
                    "a" to struct {
                        "b" to null
                    }
                },
                {
                    "a" to struct {
                        "b" to struct {
                            "c" to "2"
                        }
                    }
                }
            ).asSerializableList()

            assertPrimitiveValues(s, "[*]/a/b?/c", listOf("2"), listOf("[1]/a/b?/c"))
        }

        @Test
        fun `complex case`() {
            val s = struct {
                "a" to struct {
                    "b" to structList(
                        {
                            "c" to structList(
                                {
                                    "d" to "1"
                                },
                                {
                                    "d" to "2"
                                }
                            )
                        },
                        {
                            "c" to structList(
                                {
                                    "d" to "3"
                                },
                                {
                                    "d" to "4"
                                }
                            )
                        }
                    )
                }
            }

            assertPrimitiveValues(s, "a/b/[*]/c/[0]/d", listOf("1", "3"), listOf("a/b/[0]/c/[0]/d", "a/b/[1]/c/[0]/d"))
        }

        @Test
        fun `struct with list of simple values`() {
            val s = struct {
                "listValues" to listOf("1","2")
            }

            assertPrimitiveValues(s, "listValues/[*]", listOf("1", "2"), listOf("listValues/[0]", "listValues/[1]"))
        }

        @Test
        fun `should work for structs produced by serialized`() {
            val obj = ExampleClass(
                classValue = "value",
                entries = listOf(
                    ExampleEntry(1),
                    ExampleEntry(2)
                )
            )
            val objStruct = SerializationFactory.createSerializer().asStruct(obj)

            assertPrimitiveValues(objStruct, "classValue", listOf("value"))
            assertPrimitiveValues(objStruct, "entries/[*]/entryValue", listOf("1", "2"))
        }

        @Test
        fun `should build nested structure`() {
            val obj = struct {
                "nested_struct" to struct {
                    "string_value" to "some_value"
                }
            }

            var nested = obj["nested_struct"] as Struct
            assertThat(nested["string_value"] == "some_value")
        }

        @Test
        fun `should fail for invalid struct builder`() {
            assertApiExceptionThrown(
                    {
                        struct {
                            "nested_struct" to {
                                "string_value" to "some_value"
                            }
                        }
                    },
                    {
                        type = StructBuilderException::class
                        message = "Lambda provided, did you forgot to use 'struct' for builder function?"
                    }
            );
        }

        private fun assertPrimitiveValues(
            anyStruct: AnyStruct,
            path: String,
            expectedValues: List<String>,
            expectedPaths: List<String>? = null
        ) {
            val values = helper.getValues(anyStruct, structPath(path))
            val rawValues = values.map { it.value.asPrimitive().value }
            assertThat(rawValues).containsExactlyElementsOf(expectedValues)
            expectedPaths?.let { assertPaths(values, it) }
        }

        private fun assertStructValues(
            anyStruct: AnyStruct,
            path: String,
            expectedValues: List<ExpectedStruct>,
            expectedPaths: List<String>? = null
        ) {
            val values = helper.getValues(anyStruct, structPath(path))
            values.zip(expectedValues).forEach { (actual, expected) ->
                assertStructEquals(actual.value.asObject(), expected)
            }
            expectedPaths?.let { assertPaths(values, it) }
        }

        private fun assertPaths(
            given: List<AnyStructWithPath>,
            expectedPaths: List<String>
        ) {
            val actualPaths = given.map { it.path.value }
            assertThat(actualPaths).containsExactlyElementsOf(expectedPaths)
        }
    }
}
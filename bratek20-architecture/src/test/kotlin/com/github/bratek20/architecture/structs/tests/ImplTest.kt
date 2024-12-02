package com.github.bratek20.architecture.structs.tests

import com.github.bratek20.architecture.exceptions.assertApiExceptionThrown
import com.github.bratek20.architecture.structs.api.AnyStruct
import com.github.bratek20.architecture.structs.api.StructConversionException
import com.github.bratek20.architecture.structs.api.struct
import com.github.bratek20.architecture.structs.api.structList
import org.assertj.core.api.Assertions.assertThat
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
}
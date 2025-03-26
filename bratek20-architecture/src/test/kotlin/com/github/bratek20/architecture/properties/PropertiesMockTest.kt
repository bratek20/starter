package com.github.bratek20.architecture.properties

import com.github.bratek20.architecture.context.someContextBuilder
import com.github.bratek20.architecture.exceptions.assertApiExceptionThrown
import com.github.bratek20.architecture.properties.api.ListPropertyKey
import com.github.bratek20.architecture.properties.api.ObjectPropertyKey
import com.github.bratek20.architecture.structs.api.Struct
import com.github.bratek20.architecture.structs.api.StructList
import com.github.bratek20.architecture.structs.api.struct
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

    private lateinit var propertiesMock: PropertiesMock

    @BeforeEach
    fun setup() {
        propertiesMock = someContextBuilder()
            .withModules(PropertiesMocks())
            .buildAndGet(PropertiesMock::class.java)
    }

    @Test
    fun `should work for object keys`() {
        assertApiExceptionThrown(
            { propertiesMock.get(OBJECT_KEY) },
            {
                type = ObjectPropertyNotSetException::class
                message = "No value was set in mock for object key `key1`"
            }
        )

        
        propertiesMock.set(OBJECT_KEY, SomeClass("value"))
        val value = propertiesMock.get(OBJECT_KEY)
        assertThat(value.value).isEqualTo("value")

        // re-set to different value
        propertiesMock.set(OBJECT_KEY, SomeClass("value2"))
        val value2 = propertiesMock.get(OBJECT_KEY)
        assertThat(value2.value).isEqualTo("value2")
    }

    @Test
    fun `Should not throw serializing list containing list of objects`() {
        val SomeStructWithNestedOtherClassUniqueIds = ListPropertyKey(
            "SomeStructWithNestedOtherClassUniqueIds",
            Struct::class
        )
        val propertiesMock = PropertiesMock()
        propertiesMock.set(SomeStructWithNestedOtherClassUniqueIds, listOf(
            struct {
                "someNestedWithUniqueIds" to listOf(
                    "otherClass" to struct {
                        "uniqueId" to "1"
                    }
                )
            }
        ))

        propertiesMock.getAll()
    }


    @Test
    fun `should work for different keys instances`() {
        propertiesMock.set(ObjectPropertyKey("key1", SomeClass::class), SomeClass("value"))
        val value = propertiesMock.get(ObjectPropertyKey("key1", SomeClass::class))
        assertThat(value.value).isEqualTo("value")

        propertiesMock.set(ListPropertyKey("key2", SomeClass::class), listOf(SomeClass("y")))
        val value2 = propertiesMock.get(ListPropertyKey("key2", SomeClass::class))
        assertThat(value2).hasSize(1)
    }

    @Test
    fun `should work for list keys`() {
        assertThat(propertiesMock.get(LIST_KEY)).isEmpty()
    }

    @Test
    fun `should return getAll correctly`() {
        propertiesMock.set(OBJECT_KEY, SomeClass("x"))
        propertiesMock.set(LIST_KEY, listOf(SomeClass("y")))

        val allProperties = propertiesMock.getAll()

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
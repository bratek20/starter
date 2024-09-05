package com.github.bratek20.architecture.data

import com.github.bratek20.architecture.context.someContextBuilder
import com.github.bratek20.architecture.data.api.*
import com.github.bratek20.architecture.data.context.DataInMemoryImpl
import com.github.bratek20.architecture.exceptions.assertApiExceptionThrown
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class DataTest {
    data class SomeProperty(val value: String)
    data class SomePropertyWithAmount(val value: String, val amount: Int)
    data class OtherProperty(val otherValue: String)

    private lateinit var storage: DataStorage

    @BeforeEach
    fun setup() {
        val c = someContextBuilder()
            .withModules(
                DataInMemoryImpl(),
            )
            .build()

        storage = c.get(
            DataStorage::class.java
        )
    }

    @Nested
    inner class NormalUseCase {

        @Test
        fun shouldGetObjectProperty() {
            val expectedProp = SomeProperty("x")
            val key = ObjectDataKey("mine", SomeProperty::class)
            storage.set(key, expectedProp)

            val givenProp = storage.get(key)

            assertThat(givenProp).isEqualTo(expectedProp)
        }

        @Test
        fun shouldGetPartialObjectProperty() {
            val key = ObjectDataKey("mine", SomeProperty::class)
            val keyForTypeWithAmount = ObjectDataKey("mine", SomePropertyWithAmount::class)
            storage.set(keyForTypeWithAmount, SomePropertyWithAmount("x", 5))

            val givenProp = storage.get(key)

            assertThat(givenProp).isEqualTo(SomeProperty("x"))
        }

        @Test
        fun shouldGetListProperty() {
            val expectedProp = listOf(SomeProperty("x"), SomeProperty("y"))
            val key = ListDataKey("mine", SomeProperty::class)
            storage.set(key, expectedProp)

            val givenProp = storage.get(key)

            assertThat(givenProp).isEqualTo(expectedProp)
        }

        @Test
        fun shouldGetPartialListProperty() {
            val key = ListDataKey("mine", SomeProperty::class)
            val keyForTypeWithAmount = ListDataKey("mine", SomePropertyWithAmount::class)
            storage.set(keyForTypeWithAmount, listOf(SomePropertyWithAmount("x", 5), SomePropertyWithAmount("y", 10)))

            val givenProp = storage.get(key)

            assertThat(givenProp).isEqualTo(listOf(SomeProperty("x"), SomeProperty("y")))
        }

        @Test
        fun shouldGetAndFindMapProperty() {
            val expectedProp = listOf(SomeProperty("x"), SomeProperty("y"))
            val key = MapDataKey(
                "mine",
                SomeProperty::class
            ) { it.value }

            storage.set(key, expectedProp)

            val givenProp = storage.get(key)

            assertThat(givenProp).isEqualTo(givenProp)

            val foundProp = storage.findElement(key, "x")
            assertThat(foundProp).isEqualTo(SomeProperty("x"))

            val notFoundProp = storage.findElement(key, "z")
            assertThat(notFoundProp).isNull()
        }

        @Test
        fun shouldThrowWhenPropertyNotFound() {
            val key = ObjectDataKey("mine", SomeProperty::class)

            assertApiExceptionThrown(
                { storage.get(key) },
                {
                    type = DataNotFoundException::class
                    message = "Data `mine` not found"
                }
            )
        }

        @Test
        fun shouldThrowWhenPropertyHasDifferentKeyType() {
            val objectKey = ObjectDataKey("object", SomeProperty::class)
            val objectKeyListType = ListDataKey("object", SomeProperty::class)
            storage.set(objectKeyListType, listOf(SomeProperty("x")))

            assertApiExceptionThrown(
                { storage.get(objectKey) },
                {
                    type = DataKeyTypeException::class
                    message = "Data `object` is a list but was requested as object"
                }
            )

            val listKey = ListDataKey("list", SomeProperty::class)
            val listKeyObjectType = ObjectDataKey("list", SomeProperty::class)

            storage.set(listKeyObjectType, SomeProperty("x"))
            assertApiExceptionThrown(
                { storage.get(listKey) },
                {
                    type = DataKeyTypeException::class
                    message = "Data `list` is an object but was requested as list"
                }
            )

            val otherObjectKeyForSomeProperty = ObjectDataKey("otherObject", SomeProperty::class)
            val otherObjectKey = ObjectDataKey("otherObject", OtherProperty::class)
            storage.set(otherObjectKeyForSomeProperty, SomeProperty("x"))

            assertApiExceptionThrown(
                { storage.get(otherObjectKey) },
                {
                    type = DataKeyTypeException::class
                    message = "Data `otherObject` is not object of type `OtherProperty`: missing value for field `otherValue`"
                }
            )

            val otherListKeyForSomeProperty = ListDataKey("otherList", SomeProperty::class)
            val otherListKey = ListDataKey("otherList", OtherProperty::class)
            storage.set(otherListKeyForSomeProperty, listOf(SomeProperty("x")))
            assertApiExceptionThrown(
                { storage.get(otherListKey) },
                {
                    type = DataKeyTypeException::class
                    message = "Data `otherList` is not list with element type `OtherProperty`: missing value for field `otherValue`"
                }
            )
        }
    }

    @Nested
    inner class ExtraCases {
        @Test
        fun allOperations() {
            val key = MapDataKey(
                "list",
                SomeProperty::class
            ) { it.value }

            assertThat(storage.find(key)).isNull()
            assertApiExceptionThrown(
                { storage.get(key) },
                {
                    type = DataNotFoundException::class
                    message = "Data `list` not found"
                }
            )

            storage.set(key, listOf())
            assertThat(storage.find(key)).isEqualTo(emptyList<SomeProperty>())
            assertThat(storage.get(key)).isEqualTo(emptyList<SomeProperty>())

            storage.set(key, listOf(SomeProperty("x")))
            assertThat(storage.get(key)).isEqualTo(listOf(SomeProperty("x")))

            // elements
            assertThat(storage.findElement(key, "x")).isEqualTo(SomeProperty("x"))
            assertThat(storage.findElement(key, "y")).isNull()

            assertThat(storage.getElement(key, "x")).isEqualTo(SomeProperty("x"))
            assertApiExceptionThrown(
                { storage.getElement(key, "y") },
                {
                    type = DataElementNotFoundException::class
                    message = "Data element with id `y` for key `list` not found"
                }
            )

            assertThat(storage.addElement(key, "y", SomeProperty("y"))).isTrue()
            assertThat(storage.addElement(key, "y", SomeProperty("y"))).isFalse()
            assertThat(storage.get(key)).isEqualTo(listOf(SomeProperty("x"), SomeProperty("y")))

            assertThat(storage.findElement(key, "y")).isEqualTo(SomeProperty("y"))
            assertThat(storage.getElement(key, "y")).isEqualTo(SomeProperty("y"))

            // delete
            storage.delete(key)
            assertThat(storage.find(key)).isNull()
        }
    }
}
package com.github.bratek20.architecture.data

import com.github.bratek20.architecture.context.someContextBuilder
import com.github.bratek20.architecture.data.api.DataStorage
import com.github.bratek20.architecture.data.context.DataInMemoryImpl
import com.github.bratek20.architecture.exceptions.assertApiExceptionThrown
import com.github.bratek20.architecture.properties.api.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class DataTest {
    data class SomeProperty(val value: String)
    data class SomePropertyWithAmount(val value: String, val amount: Int)
    data class OtherProperty(val otherValue: String)

    private lateinit var storage: DataStorage

    @Nested
    inner class NormalUseCase {
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
        @Test
        fun shouldGetObjectProperty() {
            val expectedProp = SomeProperty("x")
            val key = ObjectPropertyKey("mine", SomeProperty::class)
            storage.set(key, expectedProp)

            val givenProp = storage.get(key)

            assertThat(givenProp).isEqualTo(expectedProp)
        }

        @Test
        fun shouldGetPartialObjectProperty() {
            val key = ObjectPropertyKey("mine", SomeProperty::class)
            val keyForTypeWithAmount = ObjectPropertyKey("mine", SomePropertyWithAmount::class)
            storage.set(keyForTypeWithAmount, SomePropertyWithAmount("x", 5))

            val givenProp = storage.get(key)

            assertThat(givenProp).isEqualTo(SomeProperty("x"))
        }

        @Test
        fun shouldGetListProperty() {
            val expectedProp = listOf(SomeProperty("x"), SomeProperty("y"))
            val key = ListPropertyKey("mine", SomeProperty::class)
            storage.set(key, expectedProp)

            val givenProp = storage.get(key)

            assertThat(givenProp).isEqualTo(expectedProp)
        }

        @Test
        fun shouldGetPartialListProperty() {
            val key = ListPropertyKey("mine", SomeProperty::class)
            val keyForTypeWithAmount = ListPropertyKey("mine", SomePropertyWithAmount::class)
            storage.set(keyForTypeWithAmount, listOf(SomePropertyWithAmount("x", 5), SomePropertyWithAmount("y", 10)))

            val givenProp = storage.get(key)

            assertThat(givenProp).isEqualTo(listOf(SomeProperty("x"), SomeProperty("y")))
        }

        @Test
        fun shouldGetAndFindMapProperty() {
            val expectedProp = listOf(SomeProperty("x"), SomeProperty("y"))
            val key = MapPropertyKey(
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
            val key = ObjectPropertyKey("mine", SomeProperty::class)

            assertApiExceptionThrown(
                { storage.get(key) },
                {
                    type = PropertyNotFoundException::class
                    message = "Property `mine` not found"
                }
            )
        }

        @Test
        fun shouldThrowWhenPropertyHasDifferentKeyType() {
            val objectKey = ObjectPropertyKey("object", SomeProperty::class)
            val objectKeyListType = ListPropertyKey("object", SomeProperty::class)
            storage.set(objectKeyListType, listOf(SomeProperty("x")))

            assertApiExceptionThrown(
                { storage.get(objectKey) },
                {
                    type = PropertyKeyTypeException::class
                    message = "Property `object` is a list but was requested as object"
                }
            )

            val listKey = ListPropertyKey("list", SomeProperty::class)
            val listKeyObjectType = ObjectPropertyKey("list", SomeProperty::class)

            storage.set(listKeyObjectType, SomeProperty("x"))
            assertApiExceptionThrown(
                { storage.get(listKey) },
                {
                    type = PropertyKeyTypeException::class
                    message = "Property `list` is an object but was requested as list"
                }
            )

            val otherObjectKeyForSomeProperty = ObjectPropertyKey("otherObject", SomeProperty::class)
            val otherObjectKey = ObjectPropertyKey("otherObject", OtherProperty::class)
            storage.set(otherObjectKeyForSomeProperty, SomeProperty("x"))

            assertApiExceptionThrown(
                { storage.get(otherObjectKey) },
                {
                    type = PropertyKeyTypeException::class
                    message = "Property `otherObject` is not object of type `OtherProperty`: missing value for field `otherValue`"
                }
            )

            val otherListKeyForSomeProperty = ListPropertyKey("otherList", SomeProperty::class)
            val otherListKey = ListPropertyKey("otherList", OtherProperty::class)
            storage.set(otherListKeyForSomeProperty, listOf(SomeProperty("x")))
            assertApiExceptionThrown(
                { storage.get(otherListKey) },
                {
                    type = PropertyKeyTypeException::class
                    message = "Property `otherList` is not list with element type `OtherProperty`: missing value for field `otherValue`"
                }
            )
        }
    }
}
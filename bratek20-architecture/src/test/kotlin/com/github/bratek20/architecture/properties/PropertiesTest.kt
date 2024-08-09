package com.github.bratek20.architecture.properties

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.github.bratek20.architecture.context.someContextBuilder
import com.github.bratek20.architecture.context.stableContextBuilder
import com.github.bratek20.architecture.exceptions.assertApiExceptionThrown
import com.github.bratek20.architecture.properties.api.*
import com.github.bratek20.architecture.properties.context.PropertiesImpl
import com.github.bratek20.architecture.properties.sources.inmemory.InMemoryPropertiesSource
import com.github.bratek20.architecture.properties.sources.inmemory.InMemoryPropertiesSourceImpl
import org.junit.jupiter.api.Nested

class PropertiesTest {
    data class SomeProperty(val value: String)
    data class SomePropertyWithAmount(val value: String, val amount: Int)
    data class OtherProperty(val otherValue: String)

    private lateinit var properties: Properties

    @Nested
    inner class NormalUseCase {
        private lateinit var source1: InMemoryPropertiesSource
        private lateinit var source2: InMemoryPropertiesSource

        @BeforeEach
        fun setup() {
            val c = someContextBuilder()
                .withModules(
                    PropertiesImpl(),
                    InMemoryPropertiesSourceImpl("source1"),
                    InMemoryPropertiesSourceImpl("source2")
                )
                .build()

            properties = c.get(
                Properties::class.java
            )

            val sources = c.getMany(PropertiesSource::class.java)
            source1 = sources.find { it.getName().value == "source1" } as InMemoryPropertiesSource
            source2 = sources.find { it.getName().value == "source2" } as InMemoryPropertiesSource

        }
        @Test
        fun shouldGetObjectProperty() {
            val expectedProp = SomeProperty("x")
            val key = ObjectPropertyKey("mine", SomeProperty::class)
            source1.set(key, expectedProp)

            val givenProp = properties.get(key)

            assertThat(givenProp).isEqualTo(expectedProp)
        }

        @Test
        fun shouldGetPartialObjectProperty() {
            val key = ObjectPropertyKey("mine", SomeProperty::class)
            val keyForTypeWithAmount = ObjectPropertyKey("mine", SomePropertyWithAmount::class)
            source1.set(keyForTypeWithAmount, SomePropertyWithAmount("x", 5))

            val givenProp = properties.get(key)

            assertThat(givenProp).isEqualTo(SomeProperty("x"))
        }

        @Test
        fun shouldGetListProperty() {
            val expectedProp = listOf(SomeProperty("x"), SomeProperty("y"))
            val key = ListPropertyKey("mine", SomeProperty::class)
            source1.set(key, expectedProp)

            val givenProp = properties.get(key)

            assertThat(givenProp).isEqualTo(expectedProp)
        }

        @Test
        fun shouldGetPartialListProperty() {
            val key = ListPropertyKey("mine", SomeProperty::class)
            val keyForTypeWithAmount = ListPropertyKey("mine", SomePropertyWithAmount::class)
            source1.set(keyForTypeWithAmount, listOf(SomePropertyWithAmount("x", 5), SomePropertyWithAmount("y", 10)))

            val givenProp = properties.get(key)

            assertThat(givenProp).isEqualTo(listOf(SomeProperty("x"), SomeProperty("y")))
        }

        @Test
        fun shouldGetAndFindMapProperty() {
            val expectedProp = listOf(SomeProperty("x"), SomeProperty("y"))
            val key = MapPropertyKey(
                "mine",
                SomeProperty::class
            ) { it.value }

            source1.set(key, expectedProp)

            val givenProp = properties.get(key)

            assertThat(givenProp).isEqualTo(givenProp)

            val foundProp = properties.findElement(key, "x")
            assertThat(foundProp).isEqualTo(SomeProperty("x"))

            val notFoundProp = properties.findElement(key, "z")
            assertThat(notFoundProp).isNull()
        }

        @Test
        fun shouldThrowWhenPropertyNotFound() {
            val key = ObjectPropertyKey("mine", SomeProperty::class)

            assertApiExceptionThrown(
                { properties.get(key) },
                {
                    type = PropertyNotFoundException::class
                    message = "Property `mine` not found, sources: [source1, source2]"
                }
            )
        }

        @Test
        fun shouldThrowWhenPropertyHasDifferentKeyType() {
            val objectKey = ObjectPropertyKey("object", SomeProperty::class)
            source1.setAny(objectKey, listOf(SomeProperty("x")))

            assertApiExceptionThrown(
                { properties.get(objectKey) },
                {
                    type = PropertyKeyTypeException::class
                    message = "Property `object` is a list but was requested as object"
                }
            )

            val listKey = ListPropertyKey("list", SomeProperty::class)
            source1.setAny(listKey, SomeProperty("x"))

            assertApiExceptionThrown(
                { properties.get(listKey) },
                {
                    type = PropertyKeyTypeException::class
                    message = "Property `list` is an object but was requested as list"
                }
            )

            val otherObjectKey = ObjectPropertyKey("otherObject", OtherProperty::class)
            source1.setAny(otherObjectKey, SomeProperty("x"))

            assertApiExceptionThrown(
                { properties.get(otherObjectKey) },
                {
                    type = PropertyKeyTypeException::class
                    message = "Property `otherObject` is not object of type `OtherProperty`: missing value for field `otherValue`"
                }
            )

            val otherListKey = ListPropertyKey("otherList", OtherProperty::class)
            source1.setAny(otherListKey, listOf(SomeProperty("x")))
            assertApiExceptionThrown(
                { properties.get(otherListKey) },
                {
                    type = PropertyKeyTypeException::class
                    message = "Property `otherList` is not list with element type `OtherProperty`: missing value for field `otherValue`"
                }
            )
        }
    }

    @Nested
    inner class AddSourceScope {
        @BeforeEach
        fun setup() {
            val c = stableContextBuilder()
                .withModules(
                    PropertiesImpl(),
                )
                .build()

            properties = c.get(
                Properties::class.java
            )
        }

        @Test
        fun shouldAddSource() {
            val source = InMemoryPropertiesSource("source")
            source.set(ObjectPropertyKey("key", SomeProperty::class), SomeProperty("x"))
            properties.addSource(source)

            val givenProp = properties.get(ObjectPropertyKey("key", SomeProperty::class))

            assertThat(givenProp).isEqualTo(SomeProperty("x"))
        }

        @Test
        fun shouldDetectSource() {
            val source = InMemoryPropertiesSource("source")
            source.set(ObjectPropertyKey("key", SomeProperty::class), SomeProperty("x"))
            properties.addSource(source)

            val givenProp = properties.get(ObjectPropertyKey("key", SomeProperty::class))

            assertThat(givenProp).isEqualTo(SomeProperty("x"))
        }
    }
}
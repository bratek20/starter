package pl.bratek20.architecture.properties

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pl.bratek20.architecture.context.someContextBuilder
import pl.bratek20.architecture.exceptions.assertApiExceptionThrown
import pl.bratek20.architecture.properties.api.*
import pl.bratek20.architecture.properties.context.PropertiesImpl
import pl.bratek20.architecture.properties.sources.inmemory.InMemoryPropertiesSource
import pl.bratek20.architecture.properties.sources.inmemory.InMemoryPropertiesSourceImpl

class PropertiesTest {
    data class MyProperty(val value: String)

    private lateinit var properties: Properties
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
        val expectedProp = MyProperty("x")
        val key = ObjectPropertyKey("mine", MyProperty::class)
        source1.set(key, expectedProp)

        val givenProp = properties.get(key)

        assertThat(givenProp).isEqualTo(givenProp)
    }

    @Test
    fun shouldGetListProperty() {
        val expectedProp = listOf(MyProperty("x"), MyProperty("y"))
        val key = ListPropertyKey("mine", MyProperty::class)
        source1.set(key, expectedProp)

        val givenProp = properties.get(key)

        assertThat(givenProp).isEqualTo(givenProp)
    }

    @Test
    fun shouldThrowWhenPropertyNotFound() {
        val key = ObjectPropertyKey("mine", MyProperty::class)

        assertApiExceptionThrown(
            { properties.get(key) },
            {
                type = PropertyNotFoundException::class
                message = "Property with key name `mine` not found"
            }
        )
    }

    @Test
    fun shouldThrowWhenPropertyHasDifferentKeyType() {
        val objectKey = ObjectPropertyKey("object", MyProperty::class)
        source1.set(objectKey, listOf(MyProperty("x")))

        val listKey = ListPropertyKey("list", MyProperty::class)
        source1.set(listKey, MyProperty("x"))

        assertApiExceptionThrown(
            { properties.get(objectKey) },
            {
                type = PropertyKeyTypeException::class
                message = "Property `object` is a list but was requested as object"
            }
        )
        assertApiExceptionThrown(
            { properties.get(listKey) },
            {
                type = PropertyKeyTypeException::class
                message = "Property `list` is an object but was requested as list"
            }
        )
    }
}
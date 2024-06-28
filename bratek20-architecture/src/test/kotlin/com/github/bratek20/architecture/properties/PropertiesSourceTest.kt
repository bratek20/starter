package com.github.bratek20.architecture.properties

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.github.bratek20.architecture.properties.api.ListPropertyKey
import com.github.bratek20.architecture.properties.api.ObjectPropertyKey
import com.github.bratek20.architecture.properties.api.PropertiesSource
import com.github.bratek20.architecture.serialization.api.DictionaryBuilder
import com.github.bratek20.architecture.serialization.api.DictionaryListBuilder
import com.github.bratek20.architecture.serialization.fixtures.assertSerializedValue
import com.github.bratek20.architecture.serialization.fixtures.assertSerializedValueAsDictionary
import com.github.bratek20.architecture.serialization.fixtures.assertSerializedValueAsDictionaryList

abstract class PropertiesSourceTest {
    data class SomeProperty(val value: String, val otherValue: String)
    
    protected abstract fun createAndSetupSource(): PropertiesSource

    protected abstract fun expectedName(): String

    private lateinit var source: PropertiesSource

    @BeforeEach
    fun beforeEach() {
        source = createAndSetupSource()
    }

    @Test
    fun shouldReturnExpectedName() {
        assertThat(source.getName().value).isEqualTo(expectedName())
    }

    @Test
    fun shouldReturnAllKeys() {
        assertThat(source.getAllKeys())
            .containsExactlyInAnyOrder(SOME_PROPERTY_OBJECT_KEY.name, SOME_PROPERTY_LIST_KEY.name)
    }

    @Test
    fun shouldReturnCorrectSerializedValues() {
        assertSerializedValueAsDictionary(source.getValue(SOME_PROPERTY_OBJECT_KEY.name)
        ) {
            "value" to "some value"
            "otherValue" to "other value"
        }


        assertSerializedValueAsDictionaryList(source.getValue(SOME_PROPERTY_LIST_KEY.name),
            listOf(
                {
                    "value" to "some value 1"
                    "otherValue" to "x"
                },
                {
                    "value" to "some value 2"
                    "otherValue" to "x"
                }
            )
        )
    }

    companion object {
        val SOME_PROPERTY_OBJECT_KEY: ObjectPropertyKey<SomeProperty> =
            ObjectPropertyKey("someProperty", SomeProperty::class)

        val EXPECTED_SOME_PROPERTY: SomeProperty = SomeProperty("some value", "other value")

        val SOME_PROPERTY_LIST_KEY: ListPropertyKey<SomeProperty> =
            ListPropertyKey("somePropertyList", SomeProperty::class)

        val EXPECTED_SOME_PROPERTY_LIST: List<SomeProperty> = listOf(
            SomeProperty("some value 1", "x"),
            SomeProperty("some value 2", "x")
        )
    }
}
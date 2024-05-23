package pl.bratek20.architecture.properties

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pl.bratek20.architecture.properties.api.ListPropertyKey
import pl.bratek20.architecture.properties.api.ObjectPropertyKey
import pl.bratek20.architecture.properties.api.PropertiesSource

abstract class PropertiesSourceTest {
    data class SomeProperty(val value: String, val otherValue: String)
    data class OtherProperty(val value: String, val otherValue: String)

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
    fun shouldGetExpectedPropertyObject() {
        assertThat(source.getObject(SOME_PROPERTY_OBJECT_KEY))
            .isEqualTo(EXPECTED_SOME_PROPERTY)
    }

    @Test
    fun shouldGetExpectedPropertyList() {
        assertThat(source.getList(SOME_PROPERTY_LIST_KEY))
            .isEqualTo(EXPECTED_SOME_PROPERTY_LIST)
    }

    @Test
    fun shouldSupportHasKey() {
        assertThat(source.hasKey(SOME_PROPERTY_OBJECT_KEY.name))
            .isTrue()
        assertThat(source.hasKey(SOME_PROPERTY_LIST_KEY.name))
            .isTrue()

        assertThat(source.hasKey(("notExisting")))
            .isFalse()
    }

    @Test
    fun shouldSupportIsObjectOfType() {
        assertThat(source.isObjectOfType(SOME_PROPERTY_OBJECT_KEY.name, SomeProperty::class))
            .isTrue()

        //different type
        assertThat(source.isObjectOfType(SOME_PROPERTY_OBJECT_KEY.name, OtherProperty::class))
            .isFalse()

        //list key
        assertThat(source.isObjectOfType(SOME_PROPERTY_LIST_KEY.name, SomeProperty::class))
            .isFalse()
    }

    @Test
    fun shouldSupportIsListWithWrappedType() {
        assertThat(source.isListWithWrappedType(SOME_PROPERTY_LIST_KEY.name, SomeProperty::class))
            .isTrue()

        //different wrapped type
        assertThat(source.isListWithWrappedType(SOME_PROPERTY_LIST_KEY.name, OtherProperty::class))
            .isFalse()

        //object key
        assertThat(source.isListWithWrappedType(SOME_PROPERTY_OBJECT_KEY.name, SomeProperty::class))
            .isFalse()
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
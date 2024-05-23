package pl.bratek20.architecture.properties

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import pl.bratek20.architecture.context.someContextBuilder
import pl.bratek20.architecture.properties.api.*
import pl.bratek20.architecture.properties.context.PropertiesImpl
import pl.bratek20.architecture.properties.sources.inmemory.InMemoryPropertiesSource
import pl.bratek20.architecture.properties.sources.inmemory.InMemoryPropertiesSourceImpl

class PropertiesTest {
    data class MyProperty(val value: String)

    @Test
    fun shouldGetProperty() {
        val c = someContextBuilder()
            .withModules(
                PropertiesImpl(),
                InMemoryPropertiesSourceImpl("source1"),
                InMemoryPropertiesSourceImpl("source2")
            )
            .build()

        val properties = c.get(
            Properties::class.java
        )

        val sources = c.getMany(PropertiesSource::class.java)
        val source1 = sources.find { it.getName().value == "source1" } as InMemoryPropertiesSource
        val source2 = sources.find { it.getName().value == "source2" } as InMemoryPropertiesSource

        val prop = MyProperty("x")
        source1.set(PropertyKey("mine"), prop)

        val x = properties.get(name, PropertyKey("mine"), MyProperty::class.java)

        Assertions.assertEquals(prop, x)
    }
}
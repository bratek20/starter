package com.github.bratek20.architecture.properties.sources

import com.github.bratek20.architecture.context.stableContextBuilder
import com.github.bratek20.architecture.properties.PropertiesSourceTest
import com.github.bratek20.architecture.properties.api.PropertiesSource
import com.github.bratek20.architecture.properties.sources.inmemory.InMemoryPropertiesSource
import com.github.bratek20.architecture.properties.sources.inmemory.InMemoryPropertiesSourceImpl

internal class InMemoryPropertiesSourceTest : PropertiesSourceTest() {
    override fun createAndSetupSource(): PropertiesSource {
        val source = stableContextBuilder()
            .withModules(
                InMemoryPropertiesSourceImpl("someName")
            )
            .get(InMemoryPropertiesSource::class.java)

        source.set(SOME_PROPERTY_OBJECT_KEY, EXPECTED_SOME_PROPERTY)
        source.set(SOME_PROPERTY_LIST_KEY, EXPECTED_SOME_PROPERTY_LIST)

        return source
    }

    override fun expectedName(): String {
        return "someName"
    }
}
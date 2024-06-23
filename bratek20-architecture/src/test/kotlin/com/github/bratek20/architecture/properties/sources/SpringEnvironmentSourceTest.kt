package com.github.bratek20.architecture.properties.sources

import com.github.bratek20.architecture.context.guice.GuiceContextBuilder
import com.github.bratek20.architecture.context.spring.SpringContextBuilder
import com.github.bratek20.architecture.exceptions.assertApiExceptionThrown
import com.github.bratek20.architecture.properties.PropertiesSourceTest
import com.github.bratek20.architecture.properties.api.PropertiesSource
import com.github.bratek20.architecture.properties.sources.spring.SpringEnvironmentSource
import com.github.bratek20.architecture.properties.sources.spring.SpringEnvironmentSourceImpl
import org.junit.jupiter.api.Test
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.core.env.MapPropertySource


class SpringEnvironmentSourceTest: PropertiesSourceTest() {

    override fun createAndSetupSource(): PropertiesSource {
        val context = SpringContextBuilder()
            .withModules(
                SpringEnvironmentSourceImpl("test.scope")
            )
            .build()

        val springEnv = context.get(ConfigurableEnvironment::class.java)

        val customProperties: MutableMap<String, Any> = HashMap()

        customProperties["test.scope.someProperty.value"] = "some value"
        customProperties["test.scope.someProperty.otherValue"] = "other value"

        customProperties["test.scope.somePropertyList[0].value"] = "some value 1"
        customProperties["test.scope.somePropertyList[0].otherValue"] = "x"
        customProperties["test.scope.somePropertyList[1].value"] = "some value 2"
        customProperties["test.scope.somePropertyList[1].otherValue"] = "x"

        val propertySource = MapPropertySource("customPropertySource", customProperties)
        springEnv.propertySources.addFirst(propertySource)

        return context.get(SpringEnvironmentSource::class.java)

    }

    override fun expectedName(): String {
        return "spring-environment-test.scope"
    }

    @Test
    fun shouldThrowExceptionIfNonSpringBuilderUsed() {
        assertApiExceptionThrown(
            {
                GuiceContextBuilder()
                    .withModules(
                        SpringEnvironmentSourceImpl("test.scope")
                    )
                    .build()
                    .get(SpringEnvironmentSource::class.java)
            },
            {
                message = "SpringEnvironmentSource should be used only with SpringContextBuilder"
            }
        )
    }
}
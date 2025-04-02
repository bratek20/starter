package com.github.bratek20.spring.app

import com.github.bratek20.architecture.properties.api.ListPropertyKey
import com.github.bratek20.architecture.properties.api.ObjectPropertyKey
import com.github.bratek20.architecture.properties.api.Properties
import com.github.bratek20.architecture.properties.context.PropertiesImpl
import com.github.bratek20.architecture.properties.sources.spring.SpringEnvironmentSource
import com.github.bratek20.architecture.properties.sources.spring.SpringEnvironmentSourceConfig
import com.github.bratek20.architecture.properties.sources.spring.SpringEnvironmentSourceImpl
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.core.env.ConfigurableEnvironment

data class MyProperty(
    val someInt: Int,
    val someString: String
)

class SpringEnvironmentSourceIntegrationTest {
    @Test
    fun shouldReadPropertiesFromManyApplicationYaml() {
        val context = SpringApp.run(
            modules = listOf(
                PropertiesImpl(),
                SpringEnvironmentSourceImpl(SpringEnvironmentSourceConfig(
                    prefix = "my.properties",
                    printAllPropertiesOnInit = true
                ))
            ),
            args = arrayOf( "--spring.config.additional-location=src/test/resources/additional-application.yaml")
        )

        val properties = context.get(Properties::class.java)
        val env = context.get(ConfigurableEnvironment::class.java)
        val envSource = context.get(SpringEnvironmentSource::class.java)
        envSource.init(env)

        assertThat(properties.get(ObjectPropertyKey("myProperty", MyProperty::class)))
            .isEqualTo(MyProperty(1, "a"))

        assertThat(properties.get(ListPropertyKey("myPropertyList", MyProperty::class)))
            .containsExactly(MyProperty(1, "a"), MyProperty(2, "b"))
    }
}
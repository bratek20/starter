package com.github.bratek20.architecture.properties.sources.spring

import com.github.bratek20.architecture.context.api.ContextBuilder
import com.github.bratek20.architecture.context.api.ContextModule
import com.github.bratek20.architecture.context.spring.SpringContextBuilder
import com.github.bratek20.architecture.exceptions.ApiException
import com.github.bratek20.architecture.properties.api.PropertiesSource
import com.github.bratek20.architecture.properties.api.PropertiesSourceName
import com.github.bratek20.architecture.serialization.api.SerializationType
import com.github.bratek20.architecture.serialization.api.SerializedValue
import org.springframework.core.env.ConfigurableEnvironment

class SpringEnvironmentPrefixProvider(
    val value: String
)

class SpringEnvironmentSource(
    private val prefixProvider: SpringEnvironmentPrefixProvider,
    private val env: ConfigurableEnvironment
) : PropertiesSource {

    private val prefix: String
        get() = prefixProvider.value

    override fun getName(): PropertiesSourceName {
        return PropertiesSourceName("spring-environment-$prefix")
    }

    override fun getAllKeys(): Set<String> {
        return getAllValues()
    }

    private fun getAllValues(): Set<String> {
        val x = env.propertySources.asSequence()
            .filterIsInstance<org.springframework.core.env.MapPropertySource>()
            .map { it.source as Map<String, Any> }
            .flatMap { it.keys.asSequence() }
            .toSet()

        return x.filter { it.startsWith(prefix) }.map { it.removePrefix("$prefix.") }.toSet()
    }

    private fun allValuesToJsonObject(): String {
        val allValues = getAllValues()
        val json = allValues.joinToString(",") { "\"$it\":\"${getValue(it).getValue()}\"" }
        return "{$json}"
    }

//    ["somePropertyList[0].otherValue",
//    "someProperty.otherValue",
//    "somePropertyList[1].value",
//    "someProperty.value",
//    "somePropertyList[1].otherValue",
//    "somePropertyList[0].value"]
    // json = {
    //  "somePropertyList":[{"value":"some value 1","otherValue":"x"},{"value":"some value 2","otherValue":"x"}],
    //  "someProperty":{"value":"some value","otherValue":"other value"}
    //  }

    override fun getValue(keyName: String): SerializedValue {
        return SerializedValue.create("", SerializationType.JSON)
    }
}

class SpringEnvironmentSourceImpl(
    private val prefix: String
) : ContextModule {
    override fun apply(builder: ContextBuilder) {
        if (builder !is SpringContextBuilder) {
            throw ApiException("SpringEnvironmentSource should be used only with SpringContextBuilder")
        }

        builder
            .addImplObject(SpringEnvironmentPrefixProvider::class.java, SpringEnvironmentPrefixProvider(prefix))
            .addImpl(PropertiesSource::class.java, SpringEnvironmentSource::class.java)
    }
}
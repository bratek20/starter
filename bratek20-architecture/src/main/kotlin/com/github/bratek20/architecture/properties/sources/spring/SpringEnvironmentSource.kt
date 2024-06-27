package com.github.bratek20.architecture.properties.sources.spring

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.bratek20.architecture.context.api.ContextBuilder
import com.github.bratek20.architecture.context.api.ContextModule
import com.github.bratek20.architecture.context.spring.SpringContextBuilder
import com.github.bratek20.architecture.exceptions.ApiException
import com.github.bratek20.architecture.properties.api.PropertiesSource
import com.github.bratek20.architecture.properties.api.PropertiesSourceName
import com.github.bratek20.architecture.serialization.api.SerializationType
import com.github.bratek20.architecture.serialization.api.SerializedValue
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.core.env.MapPropertySource

class SpringEnvironmentPrefixProvider(
    val value: String
)

class SpringEnvironmentSource(
    private val prefixProvider: SpringEnvironmentPrefixProvider,
    var env: ConfigurableEnvironment
) : PropertiesSource {

    private val prefix: String
        get() = prefixProvider.value

    override fun getName(): PropertiesSourceName {
        return PropertiesSourceName("spring-environment-$prefix")
    }

    override fun getAllKeys(): Set<String> {
        val obj = getJsonObject()
        return obj.fieldNames().asSequence().toSet()
    }

    private fun getJsonObject(): JsonNode {
        val map = getMap()
        val mapAsJsonObjectString = DotRepresentation(map).toJsonObject()
        return jacksonObjectMapper().readTree(mapAsJsonObjectString)
    }

    private fun getMap(): Map<String, Any> {
        val all: Sequence<MapPropertySource> = env.propertySources.asSequence()
            .filterIsInstance<MapPropertySource>()

        val result: MutableMap<String, Any> = mutableMapOf()
        all.forEach { map ->
            val filtered = map.source.filterKeys { it.startsWith(prefix) }
            result.putAll(
                filtered.map {
                    it.key.removePrefix("$prefix.") to map.getProperty(it.key)
                }
            )
        }
        return result
    }

    override fun getValue(keyName: String): SerializedValue {
        val json = getJsonObject()
        return SerializedValue.create(json.get(keyName).toString(), SerializationType.JSON)
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
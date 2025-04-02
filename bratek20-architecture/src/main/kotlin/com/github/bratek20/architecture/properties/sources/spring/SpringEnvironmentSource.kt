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

class SpringEnvironmentSourceConfig(
    val prefix: String,
    val printAllPropertiesOnInit: Boolean = false,
)

class SpringEnvironmentSource(
    private val config: SpringEnvironmentSourceConfig,
) : PropertiesSource {

    private val prefix: String
        get() = config.prefix

    private lateinit var env: ConfigurableEnvironment
    fun init(env: ConfigurableEnvironment) {
        this.env = env

        if (config.printAllPropertiesOnInit) {
            getAllPropertiesFromEnv().forEach { (key, value) ->
                println("[B20-Debug] $key: $value")
            }
        }
    }

    private fun getAllPropertiesFromEnv(): Map<String, Any> {
        val result: MutableMap<String, Any> = mutableMapOf()
        getEnv().propertySources.forEach { propertySource ->
            if (propertySource is MapPropertySource) {
                result.putAll(
                    propertySource.source.keys.map {
                        it to propertySource.getProperty(it)
                    }
                )
            }
        }
        return result
    }

    private fun getEnv(): ConfigurableEnvironment {
        if (!this::env.isInitialized) {
            throw ApiException("SpringEnvironmentSource must be initialized with ConfigurableEnvironment")
        }
        return env
    }

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
        val result: MutableMap<String, Any> = mutableMapOf()
        getAllPropertiesFromEnv().forEach { (key, value) ->
            if (key.startsWith(prefix)) {
                val strippedKey = key.removePrefix("$prefix.")
                result[strippedKey] = getEnv().getRequiredProperty(key)
            }
        }
        return result
    }

    override fun getValue(keyName: String): SerializedValue {
        val json = getJsonObject()
        return SerializedValue.create(json.get(keyName).toString(), SerializationType.JSON)
    }
}

class SpringEnvironmentSourceImpl(
    private val config: SpringEnvironmentSourceConfig
) : ContextModule {
    override fun apply(builder: ContextBuilder) {
        if (builder !is SpringContextBuilder) {
            throw ApiException("SpringEnvironmentSource should be used only with SpringContextBuilder")
        }

        builder
            .addImplObject(SpringEnvironmentSourceConfig::class.java, config)
            .addImpl(PropertiesSource::class.java, SpringEnvironmentSource::class.java)
    }
}
package pl.bratek20.architecture.properties.sources.yaml

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import pl.bratek20.architecture.context.api.ContextBuilder
import pl.bratek20.architecture.context.api.ContextModule
import pl.bratek20.architecture.exceptions.ApiException
import pl.bratek20.architecture.properties.api.PropertiesSource
import pl.bratek20.architecture.properties.api.PropertiesSourceName
import pl.bratek20.architecture.serialization.api.SerializationType
import pl.bratek20.architecture.serialization.api.SerializedValue
import java.io.File
import java.nio.file.Paths

class YamlPropertiesSource: PropertiesSource {
    constructor(propertiesPath: String) {
        this.propertiesPath = propertiesPath
    }

    var propertiesPath: String = ""
        set(value) {
            if (!value.endsWith(".yaml")) {
                throw ApiException("Yaml properties source path should end with .yaml extension")
            }
            field = value
        }

    private val mapper = ObjectMapper(YAMLFactory())
        .registerKotlinModule()
    private val jsonMapper = ObjectMapper()

    override fun getName(): PropertiesSourceName {
        return PropertiesSourceName(Paths.get(propertiesPath).toAbsolutePath().toString())
    }

    override fun getAllKeys(): Set<String> {
        return try {
            val file = getFile(propertiesPath)
            val rootNode = mapper.readTree(file)
            rootNode.fieldNames().asSequence().toSet()
        } catch (e: Exception) {
            emptySet()
        }
    }

    override fun getValue(keyName: String): SerializedValue {
        return try {
            val file = getFile(propertiesPath)
            val rootNode = mapper.readTree(file)
            val targetNode = navigateToYamlNode(rootNode, keyName)
            SerializedValue.create(
                value = jsonMapper.writeValueAsString(targetNode),
                type = SerializationType.JSON
            )
        } catch (e: Exception) {
            throw ApiException("Error while getting value for key: $keyName")
        }
    }

    private fun getFile(path: String): File {
        val nioPath = Paths.get(path)
        val file = nioPath.toFile()

        require(file.exists() && !file.isDirectory()) {
            "File does not exist or is dir for path: ${nioPath.toAbsolutePath()}"
        }
        return file
    }

    private fun navigateToYamlNode(rootNode: JsonNode, path: String): JsonNode? {
        val pathElements = path.split(".")
        var targetNode = rootNode
        for (element in pathElements) {
            targetNode = targetNode[element]
        }
        return targetNode
    }
}

class YamlPropertiesSourceImpl(
    private val propertiesPath: String = "properties.yaml"
) : ContextModule {
    override fun apply(builder: ContextBuilder) {
        builder.addImplObject(PropertiesSource::class.java, YamlPropertiesSource(propertiesPath))
    }
}

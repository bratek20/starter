package pl.bratek20.architecture.properties.sources.yaml

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.type.TypeFactory
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import lombok.SneakyThrows
import pl.bratek20.architecture.context.api.ContextBuilder
import pl.bratek20.architecture.context.api.ContextModule
import pl.bratek20.architecture.properties.api.PropertiesSource
import pl.bratek20.architecture.properties.api.PropertiesSourceName
import pl.bratek20.architecture.properties.api.PropertyKey
import java.io.File
import java.io.IOException
import java.lang.reflect.Type

class YamlPropertiesSource(
    var propertiesPath: String = "properties.yaml"
) : PropertiesSource {

    override fun getName(): PropertiesSourceName {
        return PropertiesSourceName("yaml")
    }

    override fun <T> get(key: PropertyKey, type: Class<T>): T {
        val name = key.value
        return parseYamlSection(propertiesPath, name, object : TypeReference<T>() {
            override fun getType(): Type {
                return type
            }
        })
    }

    override fun <T> getList(key: PropertyKey, type: Class<T>): List<T> {
        val name = key.value
        return parseYamlSection(propertiesPath, name, listTypeRef(type))
    }

    override fun <T> hasOfType(key: PropertyKey, type: Class<T>): Boolean {
        val name = key.value
        val mapper = ObjectMapper(YAMLFactory())
        try {
            // Load the YAML file
            val file = File(javaClass.classLoader.getResource(propertiesPath).file)
            val rootNode = mapper.readTree(file)

            // Navigate to the specified YAML path
            val pathElements = name.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            var targetNode = rootNode
            for (element in pathElements) {
                targetNode = targetNode!![element]
                if (targetNode == null) {
                    return false // Path does not exist
                }
            }

            // Attempt to parse the node into the specified type
            val parsedObject = mapper.treeToValue(targetNode, type)
            return parsedObject != null // If parsing succeeds, the path exists and is of the specified type
        } catch (e: IOException) {
            return false // Parsing failed or path does not exist
        } catch (e: IllegalArgumentException) {
            return false
        }
    }

    companion object {
        @SneakyThrows
        fun <T> parseYamlSection(resourcePath: String?, yamlPath: String, typeReference: TypeReference<T>?): T {
            val mapper = ObjectMapper(YAMLFactory())
            val file = File(YamlPropertiesSource::class.java.classLoader.getResource(resourcePath).file)
            val rootNode = mapper.readTree(file)

            // Navigate to the specified YAML path
            val pathElements = yamlPath.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            var targetNode = rootNode
            for (element in pathElements) {
                targetNode = targetNode.path(element)
            }

            // Deserialize the target node into the specified type
            return mapper.convertValue(targetNode, typeReference)
        }

        fun <T> listTypeRef(clazz: Class<T>?): TypeReference<List<T>> {
            return object : TypeReference<List<T>>() {
                override fun getType(): Type {
                    return TypeFactory.defaultInstance().constructCollectionType(
                        MutableList::class.java, clazz
                    )
                }
            }
        }
    }
}

class YamlPropertiesSourceModule : ContextModule {
    override fun apply(builder: ContextBuilder) {
        builder.addImpl(PropertiesSource::class.java, YamlPropertiesSource::class.java)
    }
}

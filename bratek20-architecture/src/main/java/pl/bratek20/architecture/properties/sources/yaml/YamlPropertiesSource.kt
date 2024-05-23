package pl.bratek20.architecture.properties.sources.yaml

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.type.TypeFactory
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import lombok.SneakyThrows
import pl.bratek20.architecture.context.api.ContextBuilder
import pl.bratek20.architecture.context.api.ContextModule
import pl.bratek20.architecture.properties.api.*
import java.io.File
import java.io.IOException
import java.lang.reflect.Type
import kotlin.reflect.KClass

class YamlPropertiesSource(
    private val propertiesPath: String
) : PropertiesSource {

    override fun getName(): PropertiesSourceName {
        return PropertiesSourceName("yaml")
    }

    override fun <T : Any> hasKey(keyName: String): Boolean {
        return privateHasKey(keyName)
    }

    override fun <T : Any> isObjectOfType(keyName: String, type: KClass<T>): Boolean {
        return hasOfType(keyName, objectTypeRef(type.java))
    }

    override fun <T : Any> isListWithWrappedType(keyName: String, type: KClass<T>): Boolean {
        return hasOfType(keyName, listTypeRef(type.java))
    }

    override fun <T : Any> getList(key: ListPropertyKey<T>): List<T> {
        return parseYamlSection(propertiesPath, key.name, listTypeRef(key.elementType.java))
    }

    override fun <T : Any> getObject(key: ObjectPropertyKey<T>): T {
        return parseYamlSection(propertiesPath, key.name, objectTypeRef(key.type.java))
    }

    private fun privateHasKey(keyName: String): Boolean {
        val name = keyName
        val mapper = ObjectMapper(YAMLFactory())
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

        return true
    }

    fun <T> hasOfType(keyName: String, typeReference: TypeReference<T>): Boolean {
        val name = keyName
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
            val parsedObject = mapper.treeToValue(targetNode, typeReference)
            return parsedObject != null // If parsing succeeds, the path exists and is of the specified type
        } catch (e: IOException) {
            return false // Parsing failed or path does not exist
        } catch (e: IllegalArgumentException) {
            return false
        }
    }

    companion object {
        @SneakyThrows
        fun <T> parseYamlSection(resourcePath: String?, yamlPath: String, typeReference: TypeReference<T>): T {
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

        fun <T> objectTypeRef(clazz: Class<T>): TypeReference<T> {
            return object : TypeReference<T>() {
                override fun getType(): Type {
                    return clazz
                }
            }
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

class YamlPropertiesSourceModule(
    private val propertiesPath: String = "properties.yaml"
) : ContextModule {
    override fun apply(builder: ContextBuilder) {
        builder.addImplObject(PropertiesSource::class.java, YamlPropertiesSource(propertiesPath))
    }
}

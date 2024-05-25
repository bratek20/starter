package pl.bratek20.architecture.properties.sources.yaml

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.type.TypeFactory
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import pl.bratek20.architecture.context.api.ContextBuilder
import pl.bratek20.architecture.context.api.ContextModule
import pl.bratek20.architecture.properties.api.ListPropertyKey
import pl.bratek20.architecture.properties.api.ObjectPropertyKey
import pl.bratek20.architecture.properties.api.PropertiesSource
import pl.bratek20.architecture.properties.api.PropertiesSourceName
import java.io.File
import java.lang.reflect.Type
import kotlin.reflect.KClass

class YamlPropertiesSource(
    private val propertiesPath: String
) : PropertiesSource {

    private val mapper = ObjectMapper(YAMLFactory())
        .registerKotlinModule()

    override fun getName(): PropertiesSourceName {
        return PropertiesSourceName("yaml")
    }

    override fun hasKey(keyName: String): Boolean {
        return checkYamlPathExists(keyName)
    }

    override fun <T : Any> isObjectOfType(keyName: String, type: KClass<T>): Boolean {
        return checkType(keyName, objectTypeRef(type.java))
    }

    override fun <T : Any> isListWithElementType(keyName: String, type: KClass<T>): Boolean {
        return checkType(keyName, listTypeRef(type.java))
    }

    override fun <T : Any> getList(key: ListPropertyKey<T>): List<T> {
        return parseYamlSection(key.name, listTypeRef(key.elementType.java))
    }

    override fun <T : Any> getObject(key: ObjectPropertyKey<T>): T {
        return parseYamlSection(key.name, objectTypeRef(key.type.java))
    }

    private fun checkYamlPathExists(keyName: String): Boolean {
        return try {
            val file = getFile(propertiesPath)
            val rootNode = mapper.readTree(file)
            navigateToYamlNode(rootNode, keyName) != null
        } catch (e: Exception) {
            false
        }
    }

    private fun <T> checkType(keyName: String, typeReference: TypeReference<T>): Boolean {
        return try {
            val file = getFile(propertiesPath)
            val rootNode = mapper.readTree(file)
            val targetNode = navigateToYamlNode(rootNode, keyName)
            targetNode != null && mapper.treeToValue(targetNode, typeReference) != null
        } catch (e: Exception) {
            false
        }
    }

    private fun <T> parseYamlSection(yamlPath: String, typeReference: TypeReference<T>): T {
        val file = getFile(propertiesPath)
        val rootNode = mapper.readTree(file)
        val targetNode = navigateToYamlNode(rootNode, yamlPath)
        return mapper.convertValue(targetNode, typeReference)
    }

    private fun getFile(path: String): File {
        return File(javaClass.classLoader.getResource(path).file)
    }

    private fun navigateToYamlNode(rootNode: JsonNode, path: String): JsonNode? {
        val pathElements = path.split(".")
        var targetNode = rootNode
        for (element in pathElements) {
            targetNode = targetNode[element]
            if (targetNode == null) {
                return null
            }
        }
        return targetNode
    }

    companion object {
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

class YamlPropertiesSourceImpl(
    private val propertiesPath: String = "properties.yaml"
) : ContextModule {
    override fun apply(builder: ContextBuilder) {
        builder.addImplObject(PropertiesSource::class.java, YamlPropertiesSource(propertiesPath))
    }
}

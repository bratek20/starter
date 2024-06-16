package pl.bratek20.architecture.properties.impl

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import pl.bratek20.architecture.exceptions.ApiException
import pl.bratek20.architecture.exceptions.ShouldNeverHappenException
import pl.bratek20.architecture.properties.api.*
import pl.bratek20.architecture.serialization.api.SerializedValue
import kotlin.reflect.KClass


class PropertiesLogic(
    private val sources: Set<PropertiesSource>
) : Properties {

    override fun <T : Any> get(key: TypedPropertyKey<T>): T {
        val source = findSourceWithKeyName(key.name)
            ?: throw PropertyNotFoundException("Property `${key.name}` not found, sources: ${sources.map { it.getName().value }}")

        val keyValue = source.getValue(key.name)
        if (key is ObjectPropertyKey<T>) {
            if (isListWithElementType(keyValue, key.type)) {
                throw PropertyKeyTypeException("Property `${key.name}` is a list but was requested as object")
            }
            if (!isObjectOfType(keyValue, key.type)) {
                throw PropertyKeyTypeException("Property `${key.name}` is not object of type `${key.type.simpleName}`")
            }
            return source.getObject(key)
        }
        if (key is ListPropertyKey<*>) {
            if (isObjectOfType(keyValue, key.elementType)) {
                throw PropertyKeyTypeException("Property `${key.name}` is an object but was requested as list")
            }
            if (!isListWithElementType(keyValue, key.elementType)) {
                throw PropertyKeyTypeException("Property `${key.name}` is not list with element type `${key.elementType.simpleName}`")
            }
            return source.getList(key) as T
        }

        throw ShouldNeverHappenException()
    }

    override fun <Id : Any, E : Any> findElement(key: MapPropertyKey<Id, E>, id: Id): E? {
        val list = get(key)
        return list.firstOrNull { key.idProvider(it) == id }
    }

    private fun findSourceWithKeyName(keyName: String): PropertiesSource? {
        return sources.firstOrNull {
            it.getAllKeys().contains(keyName)
        }
    }

    private val objectMapper = ObjectMapper()
        .registerKotlinModule()

    private fun <T: Any> isListWithElementType(value: SerializedValue, type: KClass<T>): Boolean {
        val jsonString = value.getValue()
        return try {
            val listType = objectMapper.typeFactory.constructCollectionType(List::class.java, type.java)
            val list: List<T> = objectMapper.readValue(jsonString, listType)
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun <T: Any> isObjectOfType(value: SerializedValue, type: KClass<T>): Boolean {
        val jsonString = value.getValue()
        return try {
            val objType = objectMapper.typeFactory.constructType(type.java)
            val obj: T = objectMapper.readValue(jsonString, objType)
            true
        } catch (e: Exception) {
            false
        }
    }
}

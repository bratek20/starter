package pl.bratek20.architecture.properties.api

import kotlin.reflect.KClass

interface PropertiesSource {
    fun getName(): PropertiesSourceName
    fun getAllKeys(): Set<String>
    fun hasKey(keyName: String): Boolean

    fun <T: Any> isObjectOfType(keyName: String, type: KClass<T>): Boolean
    fun <T: Any> isListWithElementType(keyName: String, type: KClass<T>): Boolean

    fun <T: Any> getList(key: ListPropertyKey<T>): List<T>
    fun <T: Any> getObject(key: ObjectPropertyKey<T>): T
}

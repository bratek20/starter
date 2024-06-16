package pl.bratek20.architecture.properties.api

import pl.bratek20.architecture.serialization.api.SerializedValue

interface PropertiesSource {
    fun getName(): PropertiesSourceName
    fun getAllKeys(): Set<String>
    fun getValue(keyName: String): SerializedValue

    //to be removed
    fun <T: Any> getList(key: ListPropertyKey<T>): List<T>
    fun <T: Any> getObject(key: ObjectPropertyKey<T>): T
}

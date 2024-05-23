package pl.bratek20.architecture.properties.api

import kotlin.reflect.KClass

data class PropertiesSourceName(val value: String)

data class PropertyKey(val value: String)

open class TypedPropertyKey<T: Any>(val name: String)

class ListPropertyKey<T: Any>(name: String, val elementType: KClass<T>)
    : TypedPropertyKey<List<T>>(name)

class ObjectPropertyKey<T: Any>(name: String, val type: KClass<T>)
    : TypedPropertyKey<T>(name)
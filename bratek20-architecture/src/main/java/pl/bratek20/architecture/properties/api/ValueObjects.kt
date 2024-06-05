package pl.bratek20.architecture.properties.api

import kotlin.reflect.KClass

data class PropertiesSourceName(val value: String)

open class TypedPropertyKey<T>(val name: String)

open class ListPropertyKey<T: Any>(name: String, val elementType: KClass<T>)
    : TypedPropertyKey<List<T>>(name)

class ObjectPropertyKey<T: Any>(name: String, val type: KClass<T>)
    : TypedPropertyKey<T>(name)

class MapPropertyKey<Id: Any, E: Any>(
    name: String,
    elementType: KClass<E>,
    val idProvider: (element: E) -> Id
) : ListPropertyKey<E>(name, elementType)
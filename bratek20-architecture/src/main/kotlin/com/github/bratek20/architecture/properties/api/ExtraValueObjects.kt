package com.github.bratek20.architecture.properties.api

import com.github.bratek20.architecture.storage.api.ListTypedKey
import com.github.bratek20.architecture.storage.api.MapTypedKey
import com.github.bratek20.architecture.storage.api.ObjectTypedKey
import com.github.bratek20.architecture.storage.api.TypedKey
import kotlin.reflect.KClass

data class PropertiesSourceName(val value: String)

abstract class PropertyKey<T>(override val name: String): TypedKey<T>

class ObjectPropertyKey<T: Any>(name: String, override val type: KClass<T>)
    : PropertyKey<T>(name), ObjectTypedKey<T>

open class ListPropertyKey<T: Any>(name: String, override val elementType: KClass<T>)
    : PropertyKey<List<T>>(name), ListTypedKey<T>

class MapPropertyKey<Id: Any, E: Any>(
    name: String,
    elementType: KClass<E>,
    override val idProvider: (element: E) -> Id
) : ListPropertyKey<E>(name, elementType), MapTypedKey<Id, E>
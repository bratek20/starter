package com.github.bratek20.architecture.data.api

import kotlin.reflect.KClass

open class TypedDataKey<T>(val name: String)

open class ListDataKey<T: Any>(name: String, val elementType: KClass<T>)
    : TypedDataKey<List<T>>(name)

class ObjectDataKey<T: Any>(name: String, val type: KClass<T>)
    : TypedDataKey<T>(name)

class MapDataKey<Id: Any, E: Any>(
    name: String,
    elementType: KClass<E>,
    val idProvider: (element: E) -> Id
) : ListDataKey<E>(name, elementType)
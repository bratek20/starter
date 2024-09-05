package com.github.bratek20.architecture.data.api

import com.github.bratek20.architecture.storage.api.ListTypedKey
import com.github.bratek20.architecture.storage.api.MapTypedKey
import com.github.bratek20.architecture.storage.api.ObjectTypedKey
import com.github.bratek20.architecture.storage.api.TypedKey
import kotlin.reflect.KClass

abstract class DataKey<T>(override val name: String): TypedKey<T>

class ObjectDataKey<T: Any>(name: String, override val type: KClass<T>)
    : DataKey<T>(name), ObjectTypedKey<T>

open class ListDataKey<T: Any>(name: String, override val elementType: KClass<T>)
    : DataKey<List<T>>(name), ListTypedKey<T>

class MapDataKey<Id: Any, E: Any>(
    name: String,
    elementType: KClass<E>,
    override val idProvider: (element: E) -> Id
) : ListDataKey<E>(name, elementType), MapTypedKey<Id, E>
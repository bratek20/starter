package com.github.bratek20.architecture.storage.api

import com.github.bratek20.architecture.data.api.ListDataKey
import com.github.bratek20.architecture.data.api.MapDataKey
import kotlin.reflect.KClass

interface TypedKey<T> {
    val name: String
}

interface ObjectTypedKey<T: Any> : TypedKey<T> {
    val type: KClass<T>
}

interface ListTypedKey<T: Any> : TypedKey<List<T>> {
    val elementType: KClass<T>
}

interface MapTypedKey<Id: Any, E: Any>: ListTypedKey<E> {
    val idProvider: (element: E) -> Id
}
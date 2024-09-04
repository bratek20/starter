package com.github.bratek20.architecture.data.api

import com.github.bratek20.architecture.properties.api.MapPropertyKey
import com.github.bratek20.architecture.properties.api.Properties
import com.github.bratek20.architecture.properties.api.PropertiesSource
import com.github.bratek20.architecture.properties.api.TypedPropertyKey
import kotlin.jvm.Throws

interface DataStorage {
    fun <T: Any> set(key: TypedPropertyKey<T>, value: T): Unit
    fun <Id: Any, E: Any> addElement(key: MapPropertyKey<Id, E>, id: Id, value: E): Boolean

    fun <T: Any> get(key: TypedPropertyKey<T>): T
    fun <Id: Any, E: Any> findElement(key: MapPropertyKey<Id, E>, id: Id): E?
}
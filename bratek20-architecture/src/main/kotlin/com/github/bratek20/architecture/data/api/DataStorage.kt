package com.github.bratek20.architecture.data.api

import com.github.bratek20.architecture.properties.api.MapPropertyKey
import com.github.bratek20.architecture.properties.api.Properties
import com.github.bratek20.architecture.properties.api.TypedPropertyKey

interface DataStorage: Properties {
    fun <T: Any> set(key: TypedPropertyKey<T>): T
    fun <Id: Any, E: Any> addElement(key: MapPropertyKey<Id, E>, id: Id): Boolean
}
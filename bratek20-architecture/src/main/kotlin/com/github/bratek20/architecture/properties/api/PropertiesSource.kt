package com.github.bratek20.architecture.properties.api

import com.github.bratek20.architecture.serialization.api.SerializedValue

interface PropertiesSource {
    fun getName(): PropertiesSourceName
    fun getAllKeys(): Set<String>
    fun getValue(keyName: String): SerializedValue
}

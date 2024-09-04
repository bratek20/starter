package com.github.bratek20.architecture.data.api

import com.github.bratek20.architecture.serialization.api.SerializedValue

interface DataStorageIntegration {
    fun setValue(keyName: String, value: SerializedValue)
    fun getValue(keyName: String): SerializedValue
}
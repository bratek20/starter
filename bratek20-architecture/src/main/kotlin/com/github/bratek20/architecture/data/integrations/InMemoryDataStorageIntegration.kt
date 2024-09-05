package com.github.bratek20.architecture.data.integrations

import com.github.bratek20.architecture.data.api.DataStorageIntegration
import com.github.bratek20.architecture.serialization.api.SerializedValue

class InMemoryDataStorageIntegration: DataStorageIntegration {
    private val data = mutableMapOf<String, SerializedValue>()

    override fun setValue(keyName: String, value: SerializedValue) {
        data[keyName] = value
    }

    override fun findValue(keyName: String): SerializedValue? {
        return data[keyName]
    }
}
package com.github.bratek20.architecture.data.integrations

import com.github.bratek20.architecture.data.api.DataManipulator
import com.github.bratek20.architecture.serialization.api.SerializedValue

class InMemoryDataManipulator: DataManipulator {
    private val data = mutableMapOf<String, SerializedValue>()

    override fun setValue(keyName: String, value: SerializedValue?) {
        if (value == null) {
            data.remove(keyName)
        } else {
            data[keyName] = value
        }
    }

    override fun findValue(keyName: String): SerializedValue? {
        return data[keyName]
    }
}
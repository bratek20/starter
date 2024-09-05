package com.github.bratek20.architecture.data.api

import com.github.bratek20.architecture.serialization.api.SerializedValue

interface DataManipulator {
    fun setValue(keyName: String, value: SerializedValue?)
    fun findValue(keyName: String): SerializedValue?
}
package com.github.bratek20.architecture.serialization.fixtures

import com.github.bratek20.architecture.serialization.api.Struct
import com.github.bratek20.architecture.serialization.context.SerializationFactory

fun asStruct(value: Any): Struct {
    return SerializationFactory.createSerializer().asStruct(value)
}
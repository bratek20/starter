package com.github.bratek20.architecture.serialization.fixtures

import com.github.bratek20.architecture.structs.api.Struct
import com.github.bratek20.architecture.serialization.context.SerializationFactory
import com.github.bratek20.architecture.structs.api.SerializableList

fun asStruct(value: Any): Struct {
    return SerializationFactory.createSerializer().asStruct(value)
}

fun asStructList(value: List<Any>): SerializableList {
    val list = SerializableList()
    value.forEach {
        list.add(asStruct(it))
    }
    return list
}
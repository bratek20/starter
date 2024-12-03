package com.github.bratek20.architecture.serialization.fixtures

import com.github.bratek20.architecture.structs.api.Struct
import com.github.bratek20.architecture.serialization.context.SerializationFactory
import com.github.bratek20.architecture.structs.api.StructList
import com.github.bratek20.architecture.structs.api.StructListBuilder

fun asStruct(value: Any): Struct {
    return SerializationFactory.createSerializer().asStruct(value)
}

fun asStructList(value: List<Any>): StructList {
    val list = StructList()
    value.forEach {
        list.add(asStruct(it))
    }
    return list
}
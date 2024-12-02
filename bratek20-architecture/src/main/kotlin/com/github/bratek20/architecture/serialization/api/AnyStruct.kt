package com.github.bratek20.architecture.serialization.api

interface AnyStruct {
    fun isStruct(): Boolean = false
    fun isList(): Boolean = false

    fun asStruct(): Struct
    fun asList(): StructList
}
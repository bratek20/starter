// DO NOT EDIT! Autogenerated by HLA tool

package com.github.bratek20.architecture.structs.api

interface AnyStruct {
    fun isObject(): Boolean

    fun isList(): Boolean

    fun isPrimitive(): Boolean

    @Throws(
        StructConversionException::class,
    )
    fun asObject(): com.github.bratek20.architecture.structs.api.Struct

    @Throws(
        StructConversionException::class,
    )
    fun asList(): SerializableList

    @Throws(
        StructConversionException::class,
    )
    fun asPrimitive(): StructPrimitive
}


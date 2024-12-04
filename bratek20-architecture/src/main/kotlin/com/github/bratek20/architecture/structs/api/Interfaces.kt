// DO NOT EDIT! Autogenerated by HLA tool

package com.github.bratek20.architecture.structs.api

interface AnyStruct {
    fun isObject(): Boolean

    fun isList(): Boolean

    @Throws(
        StructConversionException::class,
    )
    fun asObject(): Struct

    @Throws(
        StructConversionException::class,
    )
    fun asList(): StructList
}

interface AnyStructHelper {
    @Throws(
        WrongStructPathException::class,
    )
    fun getValues(anyStruct: AnyStruct, path: StructPath): List<StructValue>
}
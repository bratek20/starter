package com.github.bratek20.architecture.structs.api

class StructPrimitive(
    val value: String
): AnyStruct {
    override fun isObject(): Boolean = false
    override fun isList(): Boolean = false
    override fun isPrimitive(): Boolean = true

    override fun asObject(): Struct {
        throw StructConversionException("Tried to convert StructPrimitive to Struct")
    }

    override fun asList(): StructList {
        throw StructConversionException("Tried to convert StructPrimitive to StructList")
    }

    override fun asPrimitive(): StructPrimitive {
        return this
    }
}
package com.github.bratek20.architecture.structs.api

class Struct : AnyStruct, HashMap<String, Any?>() {
    override fun isObject(): Boolean = true
    override fun isList(): Boolean = false
    override fun isPrimitive(): Boolean = false

    override fun asObject(): Struct {
        return this
    }

    override fun asList(): SerializableList {
        throw StructConversionException("Tried to convert Struct to StructList")
    }

    override fun asPrimitive(): StructPrimitive {
        throw StructConversionException("Tried to convert Struct to StructPrimitive")
    }
}

class StructBuilder {
    private val struct = Struct()

    infix fun String.to(value: Any?): StructBuilder {
        struct[this] = value
        return this@StructBuilder
    }

    fun build(): Struct {
        return struct
    }
}

fun struct(build: StructBuilder.() -> Unit): Struct {
    return StructBuilder().apply(build).build()
}
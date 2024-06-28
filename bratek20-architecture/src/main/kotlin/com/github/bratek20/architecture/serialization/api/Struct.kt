package com.github.bratek20.architecture.serialization.api

class Struct : HashMap<String, Any?>()

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
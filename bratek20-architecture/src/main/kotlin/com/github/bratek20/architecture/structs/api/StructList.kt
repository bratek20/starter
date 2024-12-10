package com.github.bratek20.architecture.structs.api

class StructList: AnyStruct, ArrayList<Struct>() {
    override fun isObject(): Boolean = false
    override fun isList(): Boolean = true
    override fun isPrimitive(): Boolean = false

    override fun asObject(): Struct {
        throw StructConversionException("Tried to convert StructList to Struct")
    }

    override fun asList(): StructList {
        return this
    }

    override fun asPrimitive(): StructPrimitive {
        throw StructConversionException("Tried to convert StructList to StructPrimitive")
    }
}

class StructListBuilder {
    private val list = StructList()

    fun struct(build: StructBuilder.() -> Unit): StructListBuilder {
        val struct = StructBuilder().apply(build).build()
        list.add(struct)
        return this
    }

    fun build(): StructList {
        return list
    }
}

fun structList(vararg buildOps: StructBuilder.() -> Unit): StructList {
    val builder = StructListBuilder()
    buildOps.forEach { op -> builder.struct(op) }
    return builder.build()
}

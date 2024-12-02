package com.github.bratek20.architecture.structs.api

class StructList: AnyStruct, ArrayList<Struct>() {
    override fun isObject(): Boolean = false
    override fun isList(): Boolean = true

    override fun asObject(): Struct {
        throw StructConversionException("Tried to convert StructList to Struct")
    }

    override fun asList(): StructList {
        return this
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

fun structList(build: StructListBuilder.() -> Unit): StructList {
    return StructListBuilder().apply(build).build()
}
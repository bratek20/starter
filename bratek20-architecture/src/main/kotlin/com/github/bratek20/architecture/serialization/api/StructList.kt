package com.github.bratek20.architecture.serialization.api

class StructList: ArrayList<Struct>()

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
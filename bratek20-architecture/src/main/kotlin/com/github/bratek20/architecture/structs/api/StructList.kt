package com.github.bratek20.architecture.structs.api

class StructList: ArrayList<Struct>() {

    fun asSerializableList(): SerializableList {
        val serializableList = SerializableList()
        this.forEach { struct ->
            serializableList.add(struct)
        }
        return serializableList
    }

    companion object {
        fun fromSerializableList(serializableList: SerializableList): StructList {
            val s = StructList()
            serializableList.forEach { struct ->
                s.add(struct as Struct)
            }
            return s
        }
    }
}
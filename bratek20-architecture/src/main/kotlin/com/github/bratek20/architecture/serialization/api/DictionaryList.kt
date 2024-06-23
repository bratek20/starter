package com.github.bratek20.architecture.serialization.api

import com.github.bratek20.architecture.serialization.impl.SerializerLogic

class DictionaryList: ArrayList<Dictionary>()

class DictionaryListBuilder {
    private val list = DictionaryList()

    fun add(value: Dictionary): DictionaryListBuilder {
        list.add(value)
        return this
    }

    fun build(): DictionaryList {
        return list
    }

    companion object {
        fun from(value: SerializedValue): DictionaryList {
            return SerializerLogic().deserialize(value, DictionaryList::class.java)
        }
    }
}
package com.github.bratek20.architecture.serialization.api

class DictionaryList: ArrayList<Dictionary>()

class DictionaryListBuilder {
    private val list = DictionaryList()

    fun dictionary(build: DictionaryBuilder.() -> Unit): DictionaryListBuilder {
        val dictionary = DictionaryBuilder().apply(build).build()
        list.add(dictionary)
        return this
    }

    fun build(): DictionaryList {
        return list
    }
}

fun dictionaryList(build: DictionaryListBuilder.() -> Unit): DictionaryList {
    return DictionaryListBuilder().apply(build).build()
}
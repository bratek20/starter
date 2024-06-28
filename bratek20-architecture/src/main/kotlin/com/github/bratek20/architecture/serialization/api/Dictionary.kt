package com.github.bratek20.architecture.serialization.api

class Dictionary : HashMap<String, Any?>()

class DictionaryBuilder {
    private val dictionary = Dictionary()

    infix fun String.to(value: Any?): DictionaryBuilder {
        dictionary[this] = value
        return this@DictionaryBuilder
    }

    fun build(): Dictionary {
        return dictionary
    }
}

fun dictionary(build: DictionaryBuilder.() -> Unit): Dictionary {
    return DictionaryBuilder().apply(build).build()
}
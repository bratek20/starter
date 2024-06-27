package com.github.bratek20.architecture.serialization.api

class Dictionary: HashMap<String, Any?>()

class DictionaryBuilder {
    private val dictionary = Dictionary()

    fun add(key: String, value: Any?): DictionaryBuilder {
        dictionary[key] = value
        return this
    }

    fun build(): Dictionary {
        return dictionary
    }

    companion object {
        fun from(value: SerializedValue): Dictionary {
            return SerializerLogic().deserialize(value, Dictionary::class.java)
        }
    }
}
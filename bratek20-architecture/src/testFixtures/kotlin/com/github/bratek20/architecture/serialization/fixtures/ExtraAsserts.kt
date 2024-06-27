package com.github.bratek20.architecture.serialization.fixtures

import com.github.bratek20.architecture.serialization.api.Dictionary
import com.github.bratek20.architecture.serialization.api.DictionaryList
import com.github.bratek20.architecture.serialization.api.SerializedValue
import com.github.bratek20.architecture.serialization.context.SerializationFactory
import org.assertj.core.api.Assertions.assertThat

typealias ExpectedDictionary = Map<String, Any?>

fun assertDictionary(given: Dictionary, expected: ExpectedDictionary) {
    expected.forEach { (key, value) ->
        if (value is Map<*, *>) {
            assertDictionary(given[key] as Dictionary, value as ExpectedDictionary)
        } else {
            assertThat(given[key]).isEqualTo(value)
        }
    }
}

fun assertSerializedValueAsDictionary(given: SerializedValue, expected: ExpectedDictionary) {
    val serializer = SerializationFactory.createSerializer()
    assertDictionary(serializer.deserialize(given, Dictionary::class.java), expected)
}

fun assertSerializedValueAsDictionaryList(given: SerializedValue, expected: List<ExpectedDictionary>) {
    val serializer = SerializationFactory.createSerializer()
    val actual = serializer.deserialize(given, DictionaryList::class.java)
    assertThat(actual).hasSize(expected.size)
    actual.forEachIndexed { index, dictionary ->
        assertDictionary(dictionary, expected[index])
    }
}
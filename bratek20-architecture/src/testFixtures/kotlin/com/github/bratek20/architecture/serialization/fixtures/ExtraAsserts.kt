package com.github.bratek20.architecture.serialization.fixtures

import com.github.bratek20.architecture.serialization.api.*
import com.github.bratek20.architecture.serialization.context.SerializationFactory
import org.assertj.core.api.Assertions.assertThat

typealias ExpectedDictionary = DictionaryBuilder.() -> Unit

fun assertDictionaryContains(given: Dictionary, expectedInit: DictionaryBuilder.() -> Unit) {
    val expected = dictionary(expectedInit)
    assertDictionaryContains(given, expected)
}

fun assertDictionaryContains(given: Dictionary, expected: Dictionary) {
    expected.forEach { (key, value) ->
        if (value is Dictionary) {
            assertDictionaryContains(given[key] as Dictionary, value)
        } else {
            assertThat(given[key]).isEqualTo(value)
        }
    }
}

fun assertDictionaryEquals(given: Dictionary, expectedInit: DictionaryBuilder.() -> Unit) {
    val expected = dictionary(expectedInit)
    assertDictionaryEquals(given, expected)
}

fun assertDictionaryEquals(given: Dictionary, expected: Dictionary) {
    assertThat(given).isEqualTo(expected)
}

fun assertSerializedValueAsDictionary(given: SerializedValue, expected: ExpectedDictionary) {
    val serializer = SerializationFactory.createSerializer()
    assertDictionaryEquals(serializer.deserialize(given, Dictionary::class.java), expected)
}

fun assertSerializedValueAsDictionaryList(given: SerializedValue, expected: List<ExpectedDictionary>) {
    val serializer = SerializationFactory.createSerializer()
    val actual = serializer.deserialize(given, DictionaryList::class.java)
    assertThat(actual).hasSize(expected.size)
    actual.forEachIndexed { index, dictionary ->
        assertDictionaryEquals(dictionary, expected[index])
    }
}
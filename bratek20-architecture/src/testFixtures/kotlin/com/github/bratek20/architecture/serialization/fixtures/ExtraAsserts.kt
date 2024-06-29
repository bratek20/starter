package com.github.bratek20.architecture.serialization.fixtures

import com.github.bratek20.architecture.serialization.api.*
import com.github.bratek20.architecture.serialization.context.SerializationFactory
import org.assertj.core.api.Assertions.assertThat

typealias ExpectedStruct = StructBuilder.() -> Unit

fun assertStructContains(given: Struct, expectedInit: ExpectedStruct) {
    val expected = struct(expectedInit)
    assertStructContains(given, expected)
}

fun assertStructContains(given: Struct, expected: Struct) {
    expected.forEach { (key, value) ->
        if (value is Struct) {
            assertStructContains(given[key] as Struct, value)
        } else {
            assertThat(given[key]).isEqualTo(value)
        }
    }
}

fun assertStructEquals(given: Struct, expectedInit: StructBuilder.() -> Unit) {
    val expected = struct(expectedInit)
    assertStructEquals(given, expected)
}

fun assertStructEquals(given: Struct, expected: Struct) {
    assertThat(given).isEqualTo(expected)
}

fun assertSerializedValueAsStruct(given: SerializedValue, expected: ExpectedStruct) {
    val serializer = SerializationFactory.createSerializer()
    assertStructEquals(serializer.deserialize(given, Struct::class.java), expected)
}

fun assertSerializedValueAsStructList(given: SerializedValue, expected: List<ExpectedStruct>) {
    val serializer = SerializationFactory.createSerializer()
    val actual = serializer.deserialize(given, StructList::class.java)
    assertThat(actual).hasSize(expected.size)
    actual.forEachIndexed { index, struct ->
        assertStructEquals(struct, expected[index])
    }
}
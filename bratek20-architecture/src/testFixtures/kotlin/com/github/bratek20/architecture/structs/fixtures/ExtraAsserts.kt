package com.github.bratek20.architecture.structs.fixtures

import com.github.bratek20.architecture.serialization.fixtures.asStruct
import com.github.bratek20.architecture.structs.api.*
import org.assertj.core.api.Assertions.assertThat

typealias ExpectedStruct = StructBuilder.() -> Unit

fun assertStructContains(given: Struct, expectedInit: ExpectedStruct) {
    val expected = struct(expectedInit)
    assertStructContains(given, expected)
}

fun assertStructContains(given: Struct, expected: Struct) {
    expected.forEach { (key, value) ->
        if (value is Struct) {
            assertStructContains(asStruct(given[key]!!), value)
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

fun assertStructListEquals(given: SerializableList, expected: List<StructBuilder.() -> Unit>) {
    assertStructListEquals(StructList.fromSerializableList(given), expected)
}


fun assertStructListEquals(given: List<Struct>, expected: List<StructBuilder.() -> Unit>) {
    assertThat(given).hasSameSizeAs(expected)
    given.zip(expected).forEach { (g, e) ->
        assertStructEquals(g, e)
    }
}
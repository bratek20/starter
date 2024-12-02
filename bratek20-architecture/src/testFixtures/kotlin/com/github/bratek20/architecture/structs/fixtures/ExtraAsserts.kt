package com.github.bratek20.architecture.structs.fixtures

import com.github.bratek20.architecture.structs.api.Struct
import com.github.bratek20.architecture.structs.api.StructBuilder
import com.github.bratek20.architecture.structs.api.struct
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

fun assertStructListEquals(given: List<Struct>, expected: List<StructBuilder.() -> Unit>) {
    assertThat(given).hasSameSizeAs(expected)
    given.zip(expected).forEach { (g, e) ->
        assertStructEquals(g, e)
    }
}
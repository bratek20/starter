package com.github.bratek20.architecture.properties.sources.spring

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class DotRepresentationTest {
    @Test
    fun emptyCase() {
        val result = DotRepresentation(emptyMap()).toJsonObject()

        assertThat(result)
            .isEqualTo("{}")
    }

    @Test
    fun simpleCase() {
        val result = DotRepresentation(mapOf(
            "stringValue" to "abc",
            "intValue" to 123,
            "booleanValue" to true
        )).toJsonObject()

        assertThat(result)
            .isEqualTo("{\"stringValue\":\"abc\",\"intValue\":123,\"booleanValue\":true}")
    }

    @Test
    fun listCase() {
        val result = DotRepresentation(mapOf(
            "stringList[0]" to "a",
            "stringList[1]" to "b",
        )).toJsonObject()

        assertThat(result)
            .isEqualTo("{\"stringList\":[\"a\",\"b\"]}")
    }

    @Test
    fun nestedVariableCase() {
        val result = DotRepresentation(mapOf(
            "nested.value" to "abc"
        )).toJsonObject()

        assertThat(result)
            .isEqualTo("{\"nested\":{\"value\":\"abc\"}}")
    }

    @Test
    fun nestedListCase() {
        val result = DotRepresentation(mapOf(
            "nested.list[0]" to "a",
            "nested.list[1]" to "b",
        )).toJsonObject()

        assertThat(result)
            .isEqualTo("{\"nested\":{\"list\":[\"a\",\"b\"]}}")
    }
}
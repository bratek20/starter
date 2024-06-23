package com.github.bratek20.architecture.properties.sources.spring

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class JsonManipulatorTest {
    private val manipulator = JsonManipulator()

    @Test
    fun shouldMergeNestedJsons() {
        val json1 = "{\"a\":{\"b\":1}}"
        val json2 = "{\"a\":{\"c\":2}}"

        val result = manipulator.merge(json1, json2)

        assertThat(result)
            .isEqualTo("{\"a\":{\"b\":1,\"c\":2}}")
    }

    @Test
    fun shouldMergeLists() {
        val json1 = "{\"a\":[1]}"
        val json2 = "{\"a\":[null,2]}"

        val result = manipulator.merge(json1, json2)

        assertThat(result)
            .isEqualTo("{\"a\":[1,2]}")
    }

    @Test
    fun shouldMergeObjectsInLists() {
        val json1 = "{\"a\":[{\"b\":1}]}"
        val json2 = "{\"a\":[{\"c\":2}]}"

        val result = manipulator.merge(json1, json2)

        assertThat(result)
            .isEqualTo("{\"a\":[{\"b\":1,\"c\":2}]}")
    }
}
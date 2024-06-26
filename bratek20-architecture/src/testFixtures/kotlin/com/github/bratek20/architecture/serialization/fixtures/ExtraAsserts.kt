package com.github.bratek20.architecture.serialization.fixtures

import com.github.bratek20.architecture.serialization.api.Dictionary
import org.assertj.core.api.Assertions.assertThat

fun assertDictionary(given: Dictionary, expected: Map<String, Any?>) {
    expected.forEach { (key, value) ->
        if (value is Map<*, *>) {
            assertDictionary(given[key] as Dictionary, value as Map<String, Any?>)
        } else {
            assertThat(given[key]).isEqualTo(value)
        }
    }
}
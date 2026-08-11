package com.github.bratek20.utils

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CaseUtilsTest {
    @Test
    fun `camelToScreamingSnakeCase - should handle well numbers`() {
        assertThat(
            camelToScreamingSnakeCase("someKey2Property")
        ).isEqualTo(
            "SOME_KEY2_PROPERTY"
        )
    }
}
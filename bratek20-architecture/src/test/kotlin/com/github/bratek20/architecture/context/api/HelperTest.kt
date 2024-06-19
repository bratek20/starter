package com.github.bratek20.architecture.context.api

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import com.github.bratek20.architecture.context.guice.GuiceContextBuilder
import com.github.bratek20.architecture.context.someContextBuilder
import com.github.bratek20.architecture.context.spring.SpringContextBuilder

class HelperTest {
    @Test
    fun `should return someContextBuilder randomly between guice and spring impls`() {
        val result = IntRange(1, 100).map { someContextBuilder() }

        val guiceBuilders = result.count { it is GuiceContextBuilder }
        val springBuilders = result.count { it is SpringContextBuilder }

        assertThat(guiceBuilders).isGreaterThan(0)
        assertThat(springBuilders).isGreaterThan(0)
        assertThat(guiceBuilders + springBuilders).isEqualTo(100)
    }
}
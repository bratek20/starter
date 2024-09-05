package com.github.bratek20.architecture.data

import com.github.bratek20.architecture.data.api.DataManipulator
import com.github.bratek20.architecture.data.integrations.InMemoryDataManipulator
import com.github.bratek20.architecture.serialization.fixtures.serializedValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

open class DataManipulatorTest {
    open fun create(): DataManipulator {
        return InMemoryDataManipulator()
    }

    @Test
    fun shouldWork() {
        val i = create()

        assertThat(i.findValue("test")).isNull()

        i.setValue("test", serializedValue {
            value = "x"
        })
        assertThat(i.findValue("test")).isEqualTo(serializedValue {
            value = "x"
        })

        i.setValue("test2", serializedValue {
            value = "y"
        })
        assertThat(i.findValue("test2")).isEqualTo(serializedValue {
            value = "y"
        })

        i.setValue("test", serializedValue {
            value = "z"
        })
        assertThat(i.findValue("test")).isEqualTo(serializedValue {
            value = "z"
        })

        i.setValue("test", null)
        assertThat(i.findValue("test")).isNull()
    }
}
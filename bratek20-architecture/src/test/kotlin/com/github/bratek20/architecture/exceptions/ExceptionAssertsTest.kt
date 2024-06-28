package com.github.bratek20.architecture.exceptions

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class ExceptionAssertsTest {
    @Test
    fun `should assert exception with message`() {
        assertApiExceptionThrown(
            { throw ApiException("message") },
            {
                message = "message"
            }
        )
    }

    @Disabled("Only for manual testing of assertion message")
    @Test
    fun `should provide exception type and message when type is not ApiException`() {
        assertApiExceptionThrown(
            { throw IllegalStateException("message") },
            {
                message = "message"
            }
        )
    }
}
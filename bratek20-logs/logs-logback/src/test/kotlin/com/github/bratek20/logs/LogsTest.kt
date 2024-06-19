package com.github.bratek20.logs

import com.github.bratek20.architecture.context.someContextBuilder
import com.github.bratek20.logs.api.Logger
import com.github.bratek20.logs.context.Slf4jLogsImpl
import org.junit.jupiter.api.Test

class LogsTest {
    @Test
    fun test() {
        val logger = someContextBuilder()
            .withModule(Slf4jLogsImpl())
            .get(Logger::class.java)

        logger.info("info")
        logger.warn("warn", this)
    }
}
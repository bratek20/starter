package com.github.bratek20.logs

import com.github.bratek20.architecture.context.someContextBuilder
import com.github.bratek20.logs.api.Logger
import com.github.bratek20.logs.context.SystemLogsImpl
import org.junit.jupiter.api.Test

class LogsSmokeTest {
    @Test
    fun shouldWork() {
        val logger = someContextBuilder()
            .withModules(
                SystemLogsImpl()
            )
            .get(Logger::class.java)

        logger.info("Hello world!")
    }
}
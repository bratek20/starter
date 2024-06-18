package com.github.bratek20.logs

import com.github.bratek20.logs.api.Logger
import com.github.bratek20.logs.context.Slf4jLogsImpl
import com.github.bratek20.logs.slf4j.Slf4jLogger
import nl.altindag.log.LogCaptor
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import pl.bratek20.architecture.context.someContextBuilder

class Slf4jLoggerTest {

    @Test
    fun `should log message`() {
        val logger = someContextBuilder()
            .withModule(Slf4jLogsImpl())
            .get(Logger::class.java)

        val logCaptor = LogCaptor.forClass(Slf4jLogger::class.java)

        logger.info("test info")
        logger.warn("test warn")
        logger.error("test error")

        assertThat(logCaptor.infoLogs).containsExactly("test info")
        assertThat(logCaptor.warnLogs).containsExactly("test warn")
        assertThat(logCaptor.errorLogs).containsExactly("test error")
    }
}
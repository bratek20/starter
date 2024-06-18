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

    class Source1
    class Source2

    @Test
    fun `should log message for correct source`() {
        val logger = someContextBuilder()
            .withModule(Slf4jLogsImpl())
            .get(Logger::class.java)

        val defaultLogCaptor = LogCaptor.forClass(Slf4jLogger::class.java)
        val source1LogCaptor = LogCaptor.forClass(Source1::class.java)
        val source2LogCaptor = LogCaptor.forClass(Source2::class.java)

        logger.info("default")
        logger.info("source1", Source1())
        logger.info("source2", Source2())

        assertThat(defaultLogCaptor.infoLogs).containsExactly("default")
        assertThat(source1LogCaptor.infoLogs).containsExactly("source1")
        assertThat(source2LogCaptor.infoLogs).containsExactly("source2")
    }
}
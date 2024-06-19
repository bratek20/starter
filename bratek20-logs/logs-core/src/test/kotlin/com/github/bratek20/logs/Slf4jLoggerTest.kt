package com.github.bratek20.logs

import com.github.bratek20.logs.api.Logger
import com.github.bratek20.logs.context.Slf4jLogsImpl
import com.github.bratek20.logs.slf4j.Slf4jLogger
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactly
import nl.altindag.log.LogCaptor
import pl.bratek20.architecture.context.someContextBuilder

class Slf4jLoggerTest : StringSpec({

    "should log message" {
        val logger = someContextBuilder()
            .withModule(Slf4jLogsImpl())
            .get(Logger::class.java)

        val logCaptor = LogCaptor.forClass(Slf4jLogger::class.java)

        logger.info("test info")
        logger.warn("test warn")
        logger.error("test error")

        logCaptor.infoLogs shouldContainExactly listOf("test info")
        logCaptor.warnLogs shouldContainExactly listOf("test warn")
        logCaptor.errorLogs shouldContainExactly listOf("test error")
    }

    "should log message for correct source" {
        val logger = someContextBuilder()
            .withModule(Slf4jLogsImpl())
            .get(Logger::class.java)

        val defaultLogCaptor = LogCaptor.forClass(Slf4jLogger::class.java)
        val source1LogCaptor = LogCaptor.forClass(Source1::class.java)
        val source2LogCaptor = LogCaptor.forClass(Source2::class.java)

        logger.info("default")
        logger.info("source1", Source1())
        logger.info("source2", Source2())

        defaultLogCaptor.infoLogs shouldContainExactly listOf("default")
        source1LogCaptor.infoLogs shouldContainExactly listOf("source1")
        source2LogCaptor.infoLogs shouldContainExactly listOf("source2")
    }

}) {
    class Source1
    class Source2
}

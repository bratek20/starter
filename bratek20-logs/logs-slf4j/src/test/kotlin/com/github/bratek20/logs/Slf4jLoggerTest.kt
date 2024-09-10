package com.github.bratek20.logs

import com.github.bratek20.logs.api.Logger
import com.github.bratek20.logs.context.Slf4jLogsImpl
import com.github.bratek20.logs.slf4j.Slf4jLogger
import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.collections.shouldContainExactly
import nl.altindag.log.LogCaptor
import com.github.bratek20.architecture.context.someContextBuilder

class Source1
class Source2

class Slf4jLoggerTest : StringSpec({

    lateinit var logger: Logger

    beforeTest {
        logger = someContextBuilder()
            .withModule(Slf4jLogsImpl())
            .get(Logger::class.java)
    }

    "should log message for level and source if provided" {
        forAll(
            row("INFO log action",
                { msg: String, source: Any? -> logger.info(msg, source) },
                { captor: LogCaptor -> captor.infoLogs }
            ),
            row("WARN log action",
                { msg: String, source: Any? -> logger.warn(msg, source) },
                { captor: LogCaptor -> captor.warnLogs }
            ),
            row("ERROR log action",
                { msg: String, source: Any? -> logger.error(msg, source) },
                { captor: LogCaptor -> captor.errorLogs }
            )
        ) { _, logAction, messagesProvider ->
            val defaultCaptor = LogCaptor.forClass(Slf4jLogger::class.java)
            val source1Captor = LogCaptor.forClass(Source1::class.java)
            val source2Captor = LogCaptor.forClass(Source2::class.java)

            logAction("default", null)
            logAction("source1", Source1())
            logAction("source2", Source2())

            messagesProvider(defaultCaptor) shouldContainExactly listOf("default")
            messagesProvider(source1Captor) shouldContainExactly listOf("source1")
            messagesProvider(source2Captor) shouldContainExactly listOf("source2")
        }
    }
})

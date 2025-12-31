package com.github.bratek20.logs

import com.github.bratek20.architecture.context.someContextBuilder
import com.github.bratek20.logs.api.Logger
import com.github.bratek20.logs.context.Slf4jLogsImpl
import com.github.bratek20.logs.slf4j.Slf4jLogger
import nl.altindag.log.LogCaptor
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

class Source1
class Source2

class Slf4jLoggerTest {

    private lateinit var logger: Logger

    private lateinit var defaultCaptor: LogCaptor
    private lateinit var source1Captor: LogCaptor
    private lateinit var source2Captor: LogCaptor

    enum class Level { INFO, WARN, ERROR }

    @BeforeEach
    fun setup() {
        logger = someContextBuilder()
            .withModule(Slf4jLogsImpl())
            .get(Logger::class.java)

        defaultCaptor = LogCaptor.forClass(Slf4jLogger::class.java)
        source1Captor = LogCaptor.forClass(Source1::class.java)
        source2Captor = LogCaptor.forClass(Source2::class.java)
    }

    @AfterEach
    fun tearDown() {
        defaultCaptor.close()
        source1Captor.close()
        source2Captor.close()
    }

    @ParameterizedTest
    @EnumSource(Level::class)
    fun `should log message for level and source if provided`(level: Level) {
        fun log(msg: String, source: Any?) {
            when (level) {
                Level.INFO -> logger.info(msg, source)
                Level.WARN -> logger.warn(msg, source)
                Level.ERROR -> logger.error(msg, source)
            }
        }

        fun logs(captor: LogCaptor): List<String> =
            when (level) {
                Level.INFO -> captor.infoLogs
                Level.WARN -> captor.warnLogs
                Level.ERROR -> captor.errorLogs
            }

        log("default", null)
        log("source1", Source1())
        log("source2", Source2())

        assertEquals(listOf("default"), logs(defaultCaptor))
        assertEquals(listOf("source1"), logs(source1Captor))
        assertEquals(listOf("source2"), logs(source2Captor))
    }
}

package com.github.bratek20.logs

import com.github.bratek20.architecture.context.someContextBuilder
import com.github.bratek20.logs.api.Logger
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class LoggerMockTest {
    private lateinit var logger: Logger
    private lateinit var loggerMock: LoggerMock

    @BeforeEach
    fun setup() {
        val c = someContextBuilder()
            .withModules(
                LogsMocks()
            )
            .build()

        logger = c.get(Logger::class.java)
        loggerMock = c.get(LoggerMock::class.java)
    }

    @Test
    fun basic() {
        loggerMock.assertNoDebugs()
        loggerMock.assertNoInfos()
        loggerMock.assertNoWarns()
        loggerMock.assertNoErrors()

        logger.debug("debug")
        logger.info("info")
        logger.warn("warn")
        logger.error("error")

        loggerMock.assertDebugs("debug")
        loggerMock.assertInfos("info")
        loggerMock.assertWarns("warn")
        loggerMock.assertErrors("error")

        loggerMock.reset()

        loggerMock.assertNoDebugs()
        loggerMock.assertNoInfos()
        loggerMock.assertNoWarns()
        loggerMock.assertNoErrors()
    }

    class Source1
    class Source2

    @Test
    fun `should work with sources`() {
        val source1 = Source1()
        val source2 = Source2()

        logger.info("no source - 1")
        logger.info("source1 - 1", source1)
        logger.info("source2 - 1", source2)

        loggerMock.assertInfos("no source - 1", "source1 - 1", "source2 - 1")

        logger.info("source1 - 2", source1)

        loggerMock.assertInfosFor(Source1::class.java, "source1 - 1", "source1 - 2")
    }

}
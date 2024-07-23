package com.github.bratek20.logs

import com.github.bratek20.architecture.context.someContextBuilder
import com.github.bratek20.logs.api.Logger
import org.junit.jupiter.api.Test

class LoggerMockTest {
    @Test
    fun shouldWork() {
        val c = someContextBuilder()
            .withModules(LogsMocks())
            .build()

        val logger = c.get(Logger::class.java)
        val loggerMock = c.get(LoggerMock::class.java)

        loggerMock.assertNoInfos()
        loggerMock.assertNoWarns()
        loggerMock.assertNoErrors()

        logger.info("info")
        logger.warn("warn")
        logger.error("error")

        loggerMock.assertInfos("info")
        loggerMock.assertWarns("warn")
        loggerMock.assertErrors("error")
    }

}
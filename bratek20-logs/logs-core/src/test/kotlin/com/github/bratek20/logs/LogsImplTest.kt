package com.github.bratek20.logs

import com.github.bratek20.architecture.context.someContextBuilder
import com.github.bratek20.logs.api.Logger
import com.github.bratek20.logs.api.LogsApi
import com.github.bratek20.logs.context.SystemLogsImpl
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class LogsImplTest {
    private lateinit var logger: Logger
    private lateinit var logsApi: LogsApi

    @BeforeEach
    fun setup() {
        val c = someContextBuilder()
            .withModules(
                SystemLogsImpl()
            )
            .build()

        logger = c.get(Logger::class.java)
        logsApi = c.get(LogsApi::class.java)
    }

    @Test
    fun systemLoggerShouldWork() {
        logger.info("Hello world!")
    }

    @Test
    fun `should call error listeners when error logged`() {
        val errors = mutableListOf<String>()
        logsApi.addErrorListener {
            errors.add(it)
        }

        logger.info("info")
        logger.warn("warn")
        logger.error("error1")
        logger.error("error2")

        assertThat(errors).containsExactly("error1", "error2")
    }
}
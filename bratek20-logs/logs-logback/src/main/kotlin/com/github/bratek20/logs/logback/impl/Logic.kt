package com.github.bratek20.logs.logback.impl

import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.FileAppender
import com.github.bratek20.logs.logback.api.LogbackHelper
import org.slf4j.LoggerFactory

class LogbackHelperLogic : LogbackHelper {
    private var fileAppender: FileAppender<ch.qos.logback.classic.spi.ILoggingEvent>? = null

    override fun setOutputFile(path: String) {
        // Get logger context
        val loggerContext = LoggerFactory.getILoggerFactory() as LoggerContext

        // Create file appender
        fileAppender = FileAppender<ch.qos.logback.classic.spi.ILoggingEvent>().apply {
            context = loggerContext
            name = "fileAppender"
            file = path

            // Set encoder
            encoder = PatternLayoutEncoder().apply {
                context = loggerContext
                pattern = "%d{yyyy-MM-dd HH:mm:ss} [%level] %msg%n"
                start()
            }
            start()
        }

        // Attach file appender to root logger
        val rootLogger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME) as Logger
        rootLogger.addAppender(fileAppender)
    }

    override fun disableOutputFile() {
        // Detach file appender from root logger
        val rootLogger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME) as Logger
        fileAppender?.let {
            rootLogger.detachAppender(it)
            it.stop() // Ensure the appender is stopped and file is released
        }
    }
}

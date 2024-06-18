package com.github.bratek20.logs.slf4j

import com.github.bratek20.logs.api.Logger
import org.slf4j.LoggerFactory

class Slf4jLogger : Logger {
    private val defaultLogger = LoggerFactory.getLogger(Slf4jLogger::class.java)
    private val sourceLoggers = mutableMapOf<Any, org.slf4j.Logger>()

    override fun info(message: String, source: Any?) {
        if (source != null) {
            sourceLoggers.getOrPut(source) {
                LoggerFactory.getLogger(source::class.java)
            }.info(message)
            return
        }
        defaultLogger.info(message)
    }

    override fun warn(message: String, source: Any?) {
        defaultLogger.warn(message)
    }

    override fun error(message: String, source: Any?) {
        defaultLogger.error(message)
    }
}
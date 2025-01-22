package com.github.bratek20.logs.slf4j

import com.github.bratek20.logs.api.LoggerIntegration
import org.slf4j.LoggerFactory

class Slf4jLogger : LoggerIntegration {
    private val defaultLogger = LoggerFactory.getLogger(Slf4jLogger::class.java)
    private val sourceLoggers = mutableMapOf<Class<*>, org.slf4j.Logger>()

    override fun debug(message: String, source: Any?) {
        getSourceLogger(source).debug(message)
    }

    override fun info(message: String, source: Any?) {
        getSourceLogger(source).info(message)
    }

    override fun warn(message: String, source: Any?) {
        getSourceLogger(source).warn(message)
    }

    override fun error(message: String, source: Any?) {
        getSourceLogger(source).error(message)
    }

    private fun getSourceLogger(source: Any?): org.slf4j.Logger {
        if (source == null) {
            return defaultLogger
        }
        return sourceLoggers.getOrPut(source::class.java) {
            LoggerFactory.getLogger(source::class.java)
        }
    }
}
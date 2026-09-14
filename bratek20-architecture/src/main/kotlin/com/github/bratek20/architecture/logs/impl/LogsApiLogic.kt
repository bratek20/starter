package com.github.bratek20.architecture.logs.impl

import com.github.bratek20.architecture.logs.api.Logger
import com.github.bratek20.architecture.logs.api.LoggerIntegration
import com.github.bratek20.architecture.logs.api.LogsApi

class LogsApiLogic(
    private val integration: LoggerIntegration
): LogsApi, Logger {
    private val errorListeners = mutableListOf<(message: String) -> Unit>()

    override fun addErrorListener(onError: (message: String) -> Unit) {
        errorListeners.add(onError)
    }

    override fun debug(message: String, source: Any?) {
        integration.debug(message, source)
    }

    override fun info(message: String, source: Any?) {
        integration.info(message, source)
    }

    override fun warn(message: String, source: Any?) {
        integration.warn(message, source)
    }

    override fun error(message: String, source: Any?) {
        integration.error(message, source)
        errorListeners.forEach { it(message) }
    }
}
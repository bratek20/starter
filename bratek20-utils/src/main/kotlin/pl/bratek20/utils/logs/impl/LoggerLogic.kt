package pl.bratek20.utils.logs.impl

import pl.bratek20.utils.logs.api.Logger
import pl.bratek20.utils.logs.api.LoggerIntegration

class LoggerLogic(
    private val integration: LoggerIntegration
): Logger {
    override fun info(message: String) {
        integration.log("[INFO] $message")
    }

    override fun warn(message: String) {
        integration.log("[WARN] $message")
    }

    override fun error(message: String) {
        integration.log("[ERROR] $message")
    }
}
package pl.bratek20.utils.logs.impl

import pl.bratek20.utils.logs.api.Logger

class LoggerLogic: Logger {
    override fun info(message: String) {
        println("INFO: $message")
    }

    override fun warn(message: String) {
        println("WARN: $message")
    }

    override fun error(message: String) {
        println("ERROR: $message")
    }
}
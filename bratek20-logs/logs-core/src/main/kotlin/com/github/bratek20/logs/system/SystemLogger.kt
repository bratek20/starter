package com.github.bratek20.logs.system

import com.github.bratek20.logs.api.Logger

class SystemLogger : Logger {
    override fun info(message: String, source: Any?) {
        println("[INFO] $message")
    }

    override fun warn(message: String, source: Any?) {
        println("[WARN] $message")
    }

    override fun error(message: String, source: Any?) {
        println("[ERROR] $message")
    }
}
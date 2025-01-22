package com.github.bratek20.logs.api

//outgoing
interface Logger {
    fun debug(message: String, source: Any? = null)
    fun info(message: String, source: Any? = null)
    fun warn(message: String, source: Any? = null)
    fun error(message: String, source: Any? = null)
}

//incoming
interface LoggerIntegration {
    fun debug(message: String, source: Any?)
    fun info(message: String, source: Any?)
    fun warn(message: String, source: Any?)
    fun error(message: String, source: Any?)
}
package com.github.bratek20.logs.api

interface Logger {
    fun info(message: String, source: Any? = null)
    fun warn(message: String, source: Any? = null)
    fun error(message: String, source: Any? = null)
}
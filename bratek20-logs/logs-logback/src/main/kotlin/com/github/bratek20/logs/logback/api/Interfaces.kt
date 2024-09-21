package com.github.bratek20.logs.logback.api

interface LogbackHelper {
    fun setOutputFile(path: String)

    fun disableOutputFile()
}
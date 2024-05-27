package pl.bratek20.utils.logs.api

interface Logger {
    fun info(message: String)
    fun warn(message: String)
    fun error(message: String)
}
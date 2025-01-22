package com.github.bratek20.logs

import com.github.bratek20.logs.api.Logger
import com.github.bratek20.architecture.context.api.ContextBuilder
import com.github.bratek20.architecture.context.api.ContextModule
import org.assertj.core.api.Assertions.assertThat

private enum class LogLevel {
    DEBUG,
    INFO,
    WARN,
    ERROR
}

private class Log(val level: LogLevel, val message: String, val source: Any?)

class LoggerMock: Logger {
    private val logs: MutableList<Log> = mutableListOf()

    override fun debug(message: String, source: Any?) {
        logs.add(Log(LogLevel.DEBUG, message, source))
    }
    override fun info(message: String, source: Any?) {
        logs.add(Log(LogLevel.INFO, message, source))
    }
    override fun warn(message: String, source: Any?) {
        logs.add(Log(LogLevel.WARN, message, source))
    }
    override fun error(message: String, source: Any?) {
        logs.add(Log(LogLevel.ERROR, message, source))
    }

    private fun assertLogs(level: LogLevel, vararg messages: String) {
        val logs = getLogs(level)
        assertThat(logs).containsExactly(*messages)
    }
    fun assertDebugs(vararg messages: String) {
        assertLogs(LogLevel.DEBUG, *messages)
    }
    fun assertInfos(vararg messages: String) {
        assertLogs(LogLevel.INFO, *messages)
    }
    fun assertWarns(vararg messages: String) {
        assertLogs(LogLevel.WARN, *messages)
    }
    fun assertErrors(vararg messages: String) {
        assertLogs(LogLevel.ERROR, *messages)
    }

    private fun assertLogsFor(level: LogLevel, sourceType: Class<*>, vararg messages: String) {
        val logs = getLogsFor(level, sourceType)
        assertThat(logs).containsExactly(*messages)
    }
    fun assertDebugsFor(sourceType: Class<*>, vararg messages: String) {
        assertLogsFor(LogLevel.DEBUG, sourceType, *messages)
    }
    fun assertInfosFor(sourceType: Class<*>, vararg messages: String) {
        assertLogsFor(LogLevel.INFO, sourceType, *messages)
    }
    fun assertWarnsFor(sourceType: Class<*>, vararg messages: String) {
        assertLogsFor(LogLevel.WARN, sourceType, *messages)
    }
    fun assertErrorsFor(sourceType: Class<*>, vararg messages: String) {
        assertLogsFor(LogLevel.ERROR, sourceType, *messages)
    }

    private fun assertNoLogs(level: LogLevel) {
        assertThat(getLogs(level)).isEmpty()
    }
    fun assertNoDebugs() {
        assertNoLogs(LogLevel.DEBUG)
    }
    fun assertNoInfos() {
        assertNoLogs(LogLevel.INFO)
    }
    fun assertNoWarns() {
        assertNoLogs(LogLevel.WARN)
    }
    fun assertNoErrors() {
        assertNoLogs(LogLevel.ERROR)
    }

    private fun getLogs(level: LogLevel): List<String> {
        return logs.filter { it.level == level }.map { it.message }
    }
    fun getDebugs(): List<String> = getLogs(LogLevel.DEBUG)
    fun getInfos(): List<String> = getLogs(LogLevel.INFO)
    fun getWarns(): List<String> = getLogs(LogLevel.WARN)
    fun getErrors(): List<String> = getLogs(LogLevel.ERROR)

    private fun getLogsFor(level: LogLevel, sourceType: Class<*>): List<String> {
        return logs.filter { it.level == level && it.source?.javaClass == sourceType }.map { it.message }
    }
    fun getDebugsFor(sourceType: Class<*>): List<String> = getLogsFor(LogLevel.DEBUG, sourceType)
    fun getInfosFor(sourceType: Class<*>): List<String> = getLogsFor(LogLevel.INFO, sourceType)
    fun getWarnsFor(sourceType: Class<*>): List<String> = getLogsFor(LogLevel.WARN, sourceType)
    fun getErrorsFor(sourceType: Class<*>): List<String> = getLogsFor(LogLevel.ERROR, sourceType)

    fun reset() {
        logs.clear()
    }
}

class LogsMocks: ContextModule {
    override fun apply(builder: ContextBuilder) {
        builder.setImpl(Logger::class.java, LoggerMock::class.java)
    }
}
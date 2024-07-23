package com.github.bratek20.logs

import com.github.bratek20.logs.api.Logger
import com.github.bratek20.architecture.context.api.ContextBuilder
import com.github.bratek20.architecture.context.api.ContextModule
import org.assertj.core.api.Assertions.assertThat

class LoggerMock: Logger {
    private val infos: MutableList<String> = mutableListOf()
    private val warns: MutableList<String> = mutableListOf()
    private val errors: MutableList<String> = mutableListOf()

    override fun info(message: String, source: Any?) {
        infos.add(message)
    }

    override fun warn(message: String, source: Any?) {
        warns.add(message)
    }

    override fun error(message: String, source: Any?) {
        errors.add(message)
    }

    fun assertInfos(vararg messages: String) {
        assertThat(infos).containsExactly(*messages)
    }

    fun assertWarns(vararg messages: String) {
        assertThat(warns).containsExactly(*messages)
    }

    fun assertErrors(vararg messages: String) {
        assertThat(errors).containsExactly(*messages)
    }

    fun assertNoInfos() {
        assertThat(infos).isEmpty()
    }

    fun assertNoWarns() {
        assertThat(warns).isEmpty()
    }

    fun assertNoErrors() {
        assertThat(errors).isEmpty()
    }

    fun reset() {
        infos.clear()
        warns.clear()
        errors.clear()
    }
}

class LogsMocks: ContextModule {
    override fun apply(builder: ContextBuilder) {
        builder.setImpl(Logger::class.java, LoggerMock::class.java)
    }
}
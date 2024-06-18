package com.github.bratek20.logs

import com.github.bratek20.logs.api.Logger
import org.assertj.core.api.Assertions.assertThat
import pl.bratek20.architecture.context.api.ContextBuilder
import pl.bratek20.architecture.context.api.ContextModule

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
}

class LogsMocks: ContextModule {
    override fun apply(builder: ContextBuilder) {
        builder.setImpl(Logger::class.java, LoggerMock::class.java)
    }
}
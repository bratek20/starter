package com.github.bratek20.logs

import com.github.bratek20.logs.api.Logger
import io.kotest.matchers.collections.shouldContainExactly
import com.github.bratek20.architecture.context.api.ContextBuilder
import com.github.bratek20.architecture.context.api.ContextModule

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
        infos.shouldContainExactly(*messages)
    }

    fun assertWarns(vararg messages: String) {
        warns.shouldContainExactly(*messages)
    }

    fun assertErrors(vararg messages: String) {
        errors.shouldContainExactly(*messages)
    }
}

class LogsMocks: ContextModule {
    override fun apply(builder: ContextBuilder) {
        builder.setImpl(Logger::class.java, LoggerMock::class.java)
    }
}
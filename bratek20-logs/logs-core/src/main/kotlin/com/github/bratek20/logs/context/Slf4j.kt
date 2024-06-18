package com.github.bratek20.logs.context

import com.github.bratek20.logs.api.Logger
import com.github.bratek20.logs.slf4j.Slf4jLogger
import pl.bratek20.architecture.context.api.ContextBuilder
import pl.bratek20.architecture.context.api.ContextModule

class Slf4jLogs: ContextModule {
    override fun apply(builder: ContextBuilder) {
        builder.setImpl(Logger::class.java, Slf4jLogger::class.java)
    }
}
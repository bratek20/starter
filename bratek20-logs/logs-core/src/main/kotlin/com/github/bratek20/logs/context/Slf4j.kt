package com.github.bratek20.logs.context

import com.github.bratek20.logs.api.Logger
import com.github.bratek20.logs.slf4j.Slf4jLogger
import com.github.bratek20.architecture.context.api.ContextBuilder
import com.github.bratek20.architecture.context.api.ContextModule

class Slf4jLogsImpl: ContextModule {
    override fun apply(builder: ContextBuilder) {
        builder.setImpl(Logger::class.java, Slf4jLogger::class.java)
    }
}
package com.github.bratek20.logs.context

import com.github.bratek20.logs.api.Logger
import com.github.bratek20.logs.slf4j.Slf4jLogger
import com.github.bratek20.architecture.context.api.ContextBuilder
import com.github.bratek20.architecture.context.api.ContextModule
import com.github.bratek20.logs.api.LoggerIntegration

class Slf4jLogsImpl: ContextModule {
    override fun apply(builder: ContextBuilder) {
        builder
            .withModule(LogsImpl())
            .setImpl(LoggerIntegration::class.java, Slf4jLogger::class.java)
    }
}
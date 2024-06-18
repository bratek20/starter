package com.github.bratek20.logs.context

import com.github.bratek20.logs.api.Logger
import com.github.bratek20.logs.system.SystemLogger
import pl.bratek20.architecture.context.api.ContextBuilder
import pl.bratek20.architecture.context.api.ContextModule

class SystemLogs: ContextModule {
    override fun apply(builder: ContextBuilder) {
        builder.setImpl(Logger::class.java, SystemLogger::class.java)
    }
}
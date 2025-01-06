package com.github.bratek20.logs.context

import com.github.bratek20.architecture.context.api.ContextBuilder
import com.github.bratek20.architecture.context.api.ContextModule
import com.github.bratek20.logs.api.Logger
import com.github.bratek20.logs.api.LogsApi
import com.github.bratek20.logs.impl.LogsApiLogic

class LogsImpl: ContextModule {
    override fun apply(builder: ContextBuilder) {
        builder
            .setImpl(LogsApi::class.java, LogsApiLogic::class.java)
            .setImpl(Logger::class.java, LogsApiLogic::class.java)
    }
}
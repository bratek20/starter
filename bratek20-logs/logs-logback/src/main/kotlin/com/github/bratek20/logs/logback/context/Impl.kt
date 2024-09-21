package com.github.bratek20.logs.logback.context

import com.github.bratek20.architecture.context.api.ContextBuilder
import com.github.bratek20.architecture.context.api.ContextModule
import com.github.bratek20.logs.context.Slf4jLogsImpl
import com.github.bratek20.logs.logback.api.LogbackHelper
import com.github.bratek20.logs.logback.impl.LogbackHelperLogic

class LogsLogbackImpl: ContextModule {
    override fun apply(builder: ContextBuilder) {
        builder
            .withModule(Slf4jLogsImpl())
            .setImpl(LogbackHelper::class.java, LogbackHelperLogic::class.java)
    }
}
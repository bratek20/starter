package com.github.bratek20.infrastructure.httpserver.fixtures

import com.github.bratek20.architecture.context.api.ContextModule
import com.github.bratek20.infrastructure.httpserver.api.WebApp
import com.github.bratek20.infrastructure.httpserver.api.WebAppContext
import com.github.bratek20.infrastructure.httpserver.api.WebServerModule
import com.github.bratek20.logs.LoggerMock
import com.github.bratek20.logs.LogsMocks
import com.github.bratek20.spring.webapp.SpringWebApp

class TestWebApp(
    private val modules: List<ContextModule>,
    private val configs: List<Class<*>> = emptyList(),
): WebApp {
    lateinit var context: WebAppContext

    override fun run(): WebAppContext {
        val finalModules = modules + listOf(
            LogsMocks()
        )
        context = SpringWebApp.run(
            modules = finalModules,
            configs = configs,
            useRandomPort = true
        )
        return context
    }

    val port: Int
        get() = context.port

    val loggerMock: LoggerMock
        get() = context.get(LoggerMock::class.java)
}

fun runTestWebApp(
    modules: List<ContextModule> = emptyList(),
    configs: List<Class<*>> = emptyList(),
): TestWebApp {
    val app = TestWebApp(
        modules = modules,
        configs = configs,
    )
    app.run()
    return app
}
package com.github.bratek20.infrastructure.httpserver.fixtures

import com.github.bratek20.infrastructure.httpserver.api.WebApp
import com.github.bratek20.infrastructure.httpserver.api.WebAppContext
import com.github.bratek20.infrastructure.httpserver.api.WebServerModule
import com.github.bratek20.logs.LoggerMock
import com.github.bratek20.logs.LogsMocks
import com.github.bratek20.spring.webapp.SpringWebApp

class TestWebApp(
    private val modules: List<WebServerModule>
): WebApp {
    lateinit var context: WebAppContext

    override fun run(): WebAppContext {
        val finalModules = modules + listOf(
            LogsMocks()
        )
        context = SpringWebApp.run(
            modules = finalModules,
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
    modules: List<WebServerModule>
): TestWebApp {
    val app = TestWebApp(modules)
    app.run()
    return app
}
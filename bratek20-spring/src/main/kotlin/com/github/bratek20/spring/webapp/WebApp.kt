package com.github.bratek20.spring.webapp

import com.github.bratek20.architecture.context.api.ContextModule
import com.github.bratek20.architecture.context.spring.SpringContext
import com.github.bratek20.architecture.context.spring.SpringContextBuilder
import com.github.bratek20.infrastructure.httpserver.api.WebApp
import com.github.bratek20.infrastructure.httpserver.api.WebAppContext
import com.github.bratek20.infrastructure.httpserver.api.WebServerModule
import org.springframework.boot.builder.SpringApplicationBuilder

class SpringWebApp(
    private val modules: List<ContextModule>,
    private val args: Array<String> = emptyArray(),
    private val port: Int = 8080,
    private val useRandomPort: Boolean = false
): WebApp {

    override fun run(): WebAppContext {
        val allControllers = modules.filterIsInstance<WebServerModule>()
            .flatMap { it.getControllers() }
            .toTypedArray()

        val parentContext = SpringContextBuilder()
            .withModules(*modules.toTypedArray())
            .build() as SpringContext

        val finalPort = calculatePort()
        val context = SpringApplicationBuilder()
            .sources(WebAppConfig::class.java, *allControllers)
            .parent(parentContext.value)
            .run(*args, "--server.port=$finalPort")

        return WebAppContext(
            context = SpringContext(context),
            port = finalPort,
        )
    }

    private fun calculatePort(): Int {
        return if (useRandomPort) drawPort() else port
    }

    private fun drawPort(): Int {
        return (1024..65535).random()
    }

    companion object {
        fun run(
            modules: List<ContextModule> = emptyList(),
            args: Array<String> = emptyArray(),
            port: Int = 8080,
            useRandomPort: Boolean = false
        ): WebAppContext {
            val app = SpringWebApp(modules, args, port, useRandomPort)
            return app.run()
        }
    }
}


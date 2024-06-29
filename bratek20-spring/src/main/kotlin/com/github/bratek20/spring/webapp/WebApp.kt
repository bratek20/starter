package com.github.bratek20.spring.webapp

import com.github.bratek20.architecture.context.api.Context
import com.github.bratek20.architecture.context.api.ContextModule
import com.github.bratek20.architecture.context.spring.SpringContext
import com.github.bratek20.architecture.context.spring.SpringContextBuilder
import org.springframework.boot.builder.SpringApplicationBuilder

data class SpringWebServerModule(
    val impl: ContextModule,
    val controllers: List<Class<*>>,
)

class SpringWebAppContext(
    private val context: SpringContext,
    val port: Int,
): Context {
    override fun <T : Any> get(type: Class<T>): T {
        return context.get(type)
    }

    override fun <T : Any> getMany(type: Class<T>): Set<T> {
        return context.getMany(type)
    }
}

class SpringWebApp(
    private val modules: List<SpringWebServerModule>,
    private val args: Array<String> = emptyArray(),
    private val port: Int = 8080,
    private val useRandomPort: Boolean = false
) {

    fun run(): SpringWebAppContext {
        val allImpls = modules.map { it.impl }.toTypedArray()
        val allControllers = modules.flatMap { it.controllers }.toTypedArray()

        val parentContext = SpringContextBuilder()
            .withModules(*allImpls)
            .build() as SpringContext

        val finalPort = calculatePort()
        val context = SpringApplicationBuilder()
            .sources(WebAppConfig::class.java, *allControllers)
            .parent(parentContext.value)
            .run(*args, "--server.port=$finalPort")

        return SpringWebAppContext(
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
            modules: List<SpringWebServerModule> = emptyList(),
            args: Array<String> = emptyArray(),
            port: Int = 8080,
            useRandomPort: Boolean = false
        ): SpringWebAppContext {
            val app = SpringWebApp(modules, args, port, useRandomPort)
            return app.run()
        }
    }
}


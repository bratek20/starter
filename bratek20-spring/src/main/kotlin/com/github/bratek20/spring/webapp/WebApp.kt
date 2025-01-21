package com.github.bratek20.spring.webapp

import com.github.bratek20.architecture.context.api.ContextModule
import com.github.bratek20.architecture.context.spring.SpringContext
import com.github.bratek20.architecture.context.spring.SpringContextBuilder
import com.github.bratek20.infrastructure.httpserver.api.WebApp
import com.github.bratek20.infrastructure.httpserver.api.WebAppContext
import com.github.bratek20.infrastructure.httpserver.api.WebServerModule
import com.github.bratek20.logs.context.SystemLogsImpl
import org.springframework.beans.factory.config.BeanDefinitionCustomizer
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.support.GenericApplicationContext

class SpringWebApp(
    private val modules: List<ContextModule>,
    private val configs: List<Class<*>> = emptyList(),
    private val args: Array<String> = emptyArray(),
    private val port: Int = 8080,
    private val useRandomPort: Boolean = false
): WebApp {

    override fun run(): WebAppContext {
        val allControllers = modules.filterIsInstance<WebServerModule>()
            .flatMap { it.getControllers() }
            .toTypedArray()

        val applicationContext = SpringContextBuilder()
            .withModules(*modules.toTypedArray())
            .build() as SpringContext

        val sources = listOf(
            WebAppConfig::class.java,
            *allControllers,
            *configs.toTypedArray()
        )

        val finalPort = calculatePort()
        val context = SpringApplicationBuilder()
            .sources(*sources.toTypedArray())
            .parent(applicationContext.value) // Set the application context as the parent
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
            modules: List<ContextModule> = listOf(SystemLogsImpl()),
            configs: List<Class<*>> = emptyList(),
            args: Array<String> = emptyArray(),
            port: Int = 8080,
            useRandomPort: Boolean = false
        ): WebAppContext {
            val app = SpringWebApp(
                modules,
                configs,
                args,
                port,
                useRandomPort
            )
            return app.run()
        }
    }
}


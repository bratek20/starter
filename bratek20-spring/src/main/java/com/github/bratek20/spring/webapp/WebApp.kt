package com.github.bratek20.spring.webapp

import com.github.bratek20.architecture.context.spring.SpringContext
import com.github.bratek20.architecture.context.spring.SpringContextBuilder
import org.springframework.boot.builder.SpringApplicationBuilder

class SpringWebApp(
    private val contextBuilder: SpringContextBuilder = SpringContextBuilder(),
    private val args: Array<String> = emptyArray(),
    private val port: Int = 8080
) {

    fun run(): SpringContext {
        val parentContext = contextBuilder
            .setClass(HealthController::class.java)
            .build().value

        val context = SpringApplicationBuilder(WebAppConfig::class.java)
            .parent(parentContext)
            .run(*args, "--server.port=$port")

        return SpringContext(context)
    }

    companion object {
        fun run(
            contextBuilder: SpringContextBuilder = SpringContextBuilder(),
            args: Array<String> = emptyArray(),
            port: Int = 8080
        ): SpringContext {
            val app = SpringWebApp(contextBuilder, args, port)
            return app.run()
        }
    }
}


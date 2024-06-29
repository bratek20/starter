package com.github.bratek20.spring.webapp

import com.github.bratek20.architecture.context.spring.SpringContext
import org.springframework.boot.SpringApplication

class SpringWebApp(
    private val configuration: Class<*>? = null,
    private val args: Array<String> = emptyArray(),
    private val port: Int = 8080
) {

    fun run(): SpringContext {
        val configs = mutableListOf<Class<*>>(WebAppConfig::class.java)
        configuration?.let { configs.add(it) }

        val app = SpringApplication(*configs.toTypedArray())
        val context = app.run(*args, "--server.port=$port")

        return SpringContext(context)
    }

    companion object {
        fun run(
            config: Class<*>? = null,
            args: Array<String> = emptyArray(),
            port: Int = 8080
        ): SpringContext {
            val app = SpringWebApp(config, args, port)
            return app.run()
        }
    }
}


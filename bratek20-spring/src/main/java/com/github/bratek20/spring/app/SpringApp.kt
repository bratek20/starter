package com.github.bratek20.spring.app

import com.github.bratek20.architecture.context.spring.SpringContext
import org.springframework.boot.WebApplicationType
import org.springframework.boot.builder.SpringApplicationBuilder

class SpringApp(
    private val configuration: Class<*>? = null,
    private val args: Array<String> = emptyArray()
) {

    fun run(): SpringContext {
        val configs = mutableListOf<Class<*>>(AppConfig::class.java)
        configuration?.let { configs.add(it) }

        val context = SpringApplicationBuilder(*configs.toTypedArray())
            .web(WebApplicationType.NONE)
            .run(*args)

        return SpringContext(context)
    }

    companion object {
        fun run(config: Class<*>? = null, args: Array<String> = emptyArray()): SpringContext {
            val app = SpringApp(config, args)
            return app.run()
        }
    }
}

package com.github.bratek20.spring.app

import com.github.bratek20.architecture.context.api.ContextModule
import com.github.bratek20.architecture.context.spring.SpringContext
import com.github.bratek20.architecture.context.spring.SpringContextBuilder
import org.springframework.boot.WebApplicationType
import org.springframework.boot.builder.SpringApplicationBuilder

class SpringApp(
    private val modules: List<ContextModule> = emptyList(),
    private val args: Array<String> = emptyArray()
) {

    fun run(): SpringContext {
        val implContext = SpringContextBuilder()
            .withModules(*modules.toTypedArray())
            .build() as SpringContext

        val context = SpringApplicationBuilder(AppConfig::class.java)
            .web(WebApplicationType.NONE)
            .parent(implContext.value)
            .run(*args)

        return SpringContext(context)
    }

    companion object {
        fun run(modules: List<ContextModule> = emptyList(), args: Array<String> = emptyArray()): SpringContext {
            val app = SpringApp(modules, args)
            return app.run()
        }
    }
}

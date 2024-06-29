package com.github.bratek20.infrastructure.httpserver.fixtures

import com.github.bratek20.infrastructure.httpserver.api.WebApp
import com.github.bratek20.infrastructure.httpserver.api.WebAppContext
import com.github.bratek20.infrastructure.httpserver.api.WebServerModule
import com.github.bratek20.spring.webapp.SpringWebApp

class TestWebApp(
    private val modules: List<WebServerModule>
): WebApp {
    override fun run(): WebAppContext {
        return SpringWebApp.run(
            modules = modules,
            useRandomPort = true
        )
    }
}
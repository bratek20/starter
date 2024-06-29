package com.github.bratek20.infrastructure.httpserver.api

import com.github.bratek20.architecture.context.api.ContextModule

interface WebServerModule {
    fun getImpl(): ContextModule
    fun getControllers(): List<Class<*>>
}
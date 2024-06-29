package com.github.bratek20.infrastructure.httpserver.api

import com.github.bratek20.architecture.context.api.Context
import com.github.bratek20.architecture.context.spring.SpringContext

class WebAppContext(
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
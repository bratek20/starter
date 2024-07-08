package com.github.bratek20.architecture.context.api

interface ContextBuilder {
    fun <T> setClass(type: Class<T>): ContextBuilder
    fun <I, T: I> setImpl(interfaceType: Class<I>, implementationType: Class<T>): ContextBuilder
    fun <T: Any> setObject(obj: T): ContextBuilder
    fun <I: Any, T: I> setImplObject(interfaceType: Class<I>, implementationObj: T): ContextBuilder

    //fun <T> expectMany(type: Class<T>): ContextBuilder
    fun <T> addClass(type: Class<T>): ContextBuilder
    fun <I, T: I> addImpl(interfaceType: Class<I>, implementationType: Class<T>): ContextBuilder
    fun <T: Any> addObject(obj: T): ContextBuilder
    fun <I: Any, T: I> addImplObject(interfaceType: Class<I>, implementationObj: T): ContextBuilder

    fun withModule(module: ContextModule): ContextBuilder
    fun withDefaultModule(module: ContextModule): ContextBuilder

    fun build(): Context

    // extensions
    fun withModules(vararg modules: ContextModule): ContextBuilder {
        modules.forEach { withModule(it) }
        return this
    }

    fun withDefaultModules(vararg modules: ContextModule): ContextBuilder {
        modules.forEach { withDefaultModule(it) }
        return this
    }

    fun <T: Any> get(type: Class<T>): T {
        return build().get(type)
    }
}
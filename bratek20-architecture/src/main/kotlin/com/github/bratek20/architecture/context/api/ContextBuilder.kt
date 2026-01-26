package com.github.bratek20.architecture.context.api

interface ContextBuilder {
    fun <T> setClass(type: Class<T>): ContextBuilder
    fun setClasses(vararg types: Class<*>): ContextBuilder {
        types.forEach { setClass(it) }
        return this
    }

    fun <T> addClass(type: Class<T>): ContextBuilder

    fun <I, T: I> setImpl(interfaceType: Class<I>, implementationType: Class<T>): ContextBuilder
    fun <I, T: I> addImpl(interfaceType: Class<I>, implementationType: Class<T>): ContextBuilder

    fun <I: Any, T: I> setImplObject(interfaceType: Class<I>, implementationObj: T): ContextBuilder
    fun <I: Any, T: I> addImplObject(interfaceType: Class<I>, implementationObj: T): ContextBuilder

    fun withModule(module: ContextModule): ContextBuilder

    fun withModules(vararg modules: ContextModule): ContextBuilder {
        modules.forEach { withModule(it) }
        return this
    }

    fun withParent(parent: Context): ContextBuilder

    fun build(): Context

    @Deprecated(
        "ContextBuilder.get can be confused with Context.get resulting in ugly bug",
        ReplaceWith("buildAndGet(type)"),
    )
    fun <T: Any> get(type: Class<T>): T {
        return buildAndGet(type)
    }

    fun <T: Any> buildAndGet(type: Class<T>): T {
        return build().get(type)
    }
}
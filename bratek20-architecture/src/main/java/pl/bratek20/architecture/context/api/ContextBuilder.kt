package pl.bratek20.architecture.context.api

interface ContextBuilder {
    fun <T> setClass(type: Class<T>): ContextBuilder
    fun <T> addClass(type: Class<T>): ContextBuilder

    fun <I, T: I> setImpl(interfaceType: Class<I>, implementationType: Class<T>): ContextBuilder
    fun <I, T: I> addImpl(interfaceType: Class<I>, implementationType: Class<T>): ContextBuilder

    fun <I: Any, T: I> setImplObject(interfaceType: Class<I>, implementationObj: T): ContextBuilder
    fun <I: Any, T: I> addImplObject(interfaceType: Class<I>, implementationObj: T): ContextBuilder

    fun withModule(module: ContextModule): ContextBuilder

    fun build(): Context
}
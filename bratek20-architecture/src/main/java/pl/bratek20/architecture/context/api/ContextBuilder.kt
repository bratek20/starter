package pl.bratek20.architecture.context.api

interface ContextBuilder {
    fun <T> setClass(type: Class<T>): ContextBuilder
    fun <T> addClass(type: Class<T>): ContextBuilder

    fun <I, T: I> setImpl(interfaceType: Class<I>, implementationType: Class<T>): ContextBuilder
    fun <I, T: I> addImpl(interfaceType: Class<I>, implementationType: Class<T>): ContextBuilder

    fun setObject(obj: Any): ContextBuilder
    fun addObject(obj: Any): ContextBuilder

    fun withModule(module: ContextModule): ContextBuilder

    fun build(): Context
}
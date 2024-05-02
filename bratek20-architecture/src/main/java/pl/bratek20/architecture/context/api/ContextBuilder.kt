package pl.bratek20.architecture.context.api

interface ContextBuilder {
    fun <T> withClass(type: Class<T>): ContextBuilder

    fun <I, T: I> bind(interfaceType: Class<I>, implementationType: Class<T>): ContextBuilder

    fun withModule(module: ContextModule): ContextBuilder

    fun withObject(obj: Any): ContextBuilder

    fun build(): Context
}
package pl.bratek20.architecture.context.api

interface ContextBuilder {
    fun withClass(type: Class<*>): ContextBuilder

    fun <I, T: I> withBind(interfaceType: Class<I>, implementationType: Class<T>): ContextBuilder

    fun withModule(module: ContextModule): ContextBuilder

    fun withObject(obj: Any): ContextBuilder

    fun build(): Context
}
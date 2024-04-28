package pl.bratek20.architecture.context.api

interface ContextBuilder {
    fun withClass(type: Class<*>): ContextBuilder
    fun <I, T: I> withBind(interfaceType: Class<I>, implementationType: Class<T>): ContextBuilder
    fun build(): Context
}
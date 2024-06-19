package pl.bratek20.architecture.context.api

interface ContextModule {
    fun apply(builder: ContextBuilder)
}
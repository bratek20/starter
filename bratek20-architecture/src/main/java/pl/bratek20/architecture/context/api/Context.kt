package pl.bratek20.architecture.context.api

interface Context {
    fun <T> get(type: Class<T>): T
}
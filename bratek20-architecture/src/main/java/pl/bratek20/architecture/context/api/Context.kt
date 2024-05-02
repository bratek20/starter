package pl.bratek20.architecture.context.api

interface Context {
    @Throws(
        ClassNotFoundException::class
    )
    fun <T> get(type: Class<T>): T
}
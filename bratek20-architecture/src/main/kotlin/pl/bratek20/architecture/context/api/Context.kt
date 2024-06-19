package pl.bratek20.architecture.context.api

interface Context {
    @Throws(
        ClassNotFoundInContextException::class
    )
    fun <T: Any> get(type: Class<T>): T

    fun <T: Any> getMany(type: Class<T>): Set<T>
}
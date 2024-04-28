package pl.bratek20.architecture.context.api

interface Context {
    @Throws(
        ClassNotFoundException::class,
        MultipleClassesFoundException::class
    )
    fun <T> get(type: Class<T>): T

    fun <T> getMany(type: Class<T>): List<T>
}
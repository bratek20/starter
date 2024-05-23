package pl.bratek20.architecture.properties.api

interface Properties {
    fun <T: Any> get(key: TypedPropertyKey<T>): T
}

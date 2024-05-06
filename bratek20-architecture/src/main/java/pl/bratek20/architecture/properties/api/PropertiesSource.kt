package pl.bratek20.architecture.properties.api

interface PropertiesSource {
    fun getName(): PropertiesSourceName

    fun <T> get(key: PropertyKey, type: Class<T>): T

    fun <T> getList(key: PropertyKey, type: Class<T>): List<T>

    fun <T> hasOfType(key: PropertyKey, type: Class<T>): Boolean
}

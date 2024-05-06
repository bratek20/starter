package pl.bratek20.architecture.properties.api

interface Properties {
    fun <T> get(source: PropertiesSourceName, key: PropertyKey, type: Class<T>): T
    fun <T> getList(source: PropertiesSourceName, key: PropertyKey, type: Class<T>): List<T>
}

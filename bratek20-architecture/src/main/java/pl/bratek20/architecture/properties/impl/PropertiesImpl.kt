package pl.bratek20.architecture.properties.impl

import pl.bratek20.architecture.properties.api.*

class PropertiesImpl(
    private val sources: List<PropertiesSource>
) : Properties {
    override fun <T> get(source: PropertiesSourceName, key: PropertyKey, type: Class<T>): T {
        return sources.find { it.getName() == source }?.get(key, type) ?: throw PropertiesSourceNotFoundException(source.value)
    }

    override fun <T> getList(source: PropertiesSourceName, key: PropertyKey, type: Class<T>): List<T> {
        return sources.find { it.getName() == source }?.getList(key, type) ?: throw PropertiesSourceNotFoundException(source.value)
    }
}

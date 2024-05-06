package pl.bratek20.architecture.properties.sources.inmemory

import pl.bratek20.architecture.properties.api.PropertiesSource
import pl.bratek20.architecture.properties.api.PropertiesSourceName
import pl.bratek20.architecture.properties.api.PropertyKey

class InMemoryPropertiesSource : PropertiesSource {
    companion object {
        val name = PropertiesSourceName("inMemory")
    }

    private val properties: MutableMap<PropertyKey, Any> = HashMap()

    fun set(key: PropertyKey, property: Any) {
        properties[key] = property
    }

    override fun getName(): PropertiesSourceName {
        return name
    }

    override fun <T> get(key: PropertyKey, type: Class<T>): T {
        return type.cast(properties[key])
    }

    override fun <T> getList(key: PropertyKey, type: Class<T>): List<T> {
        val property = properties[key]
        return property as List<T>
    }

    override fun <T> hasOfType(key: PropertyKey, type: Class<T>): Boolean {
        val property = properties[key]
        return type.isInstance(property)
    }
}

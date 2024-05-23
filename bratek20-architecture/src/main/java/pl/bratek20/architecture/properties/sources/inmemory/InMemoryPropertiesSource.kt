package pl.bratek20.architecture.properties.sources.inmemory

import pl.bratek20.architecture.context.api.ContextBuilder
import pl.bratek20.architecture.context.api.ContextModule
import pl.bratek20.architecture.properties.api.PropertiesSource
import pl.bratek20.architecture.properties.api.PropertiesSourceName
import pl.bratek20.architecture.properties.api.PropertyKey

class InMemoryPropertiesSource(
    private val name: String
) : PropertiesSource {

    private val properties: MutableMap<PropertyKey, Any> = HashMap()

    fun set(key: PropertyKey, property: Any) {
        properties[key] = property
    }

    override fun getName(): PropertiesSourceName {
        return PropertiesSourceName(name)
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

class InMemoryPropertiesSourceImpl(
    private val name: String
): ContextModule {
    override fun apply(builder: ContextBuilder) {
        builder.addImplObject(PropertiesSource::class.java, InMemoryPropertiesSource(name))
    }
}

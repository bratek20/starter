package pl.bratek20.architecture.properties

import pl.bratek20.architecture.context.api.ContextBuilder
import pl.bratek20.architecture.context.api.ContextModule
import pl.bratek20.architecture.properties.api.Properties
import pl.bratek20.architecture.properties.api.PropertiesSourceName
import pl.bratek20.architecture.properties.api.PropertyKey

class PropertiesMock : Properties {
    private val values: MutableMap<PropertiesSourceName, MutableMap<PropertyKey, Any>> = HashMap()

    override fun <T> get(source: PropertiesSourceName, key: PropertyKey, type: Class<T>): T {
        return type.cast(values[source]?.get(key))
    }

    override fun <T> getList(source: PropertiesSourceName, key: PropertyKey, type: Class<T>): List<T> {
        return listOf(values[source]?.get(key)).map { type.cast(it) }
    }

    fun setProperty(source: PropertiesSourceName, key: PropertyKey, value: Any) {
        values.computeIfAbsent(source) { HashMap() }[key] = value
    }
}

class PropertiesMockContextModule : ContextModule {
    override fun apply(builder: ContextBuilder) {
        builder.setImpl(Properties::class.java, PropertiesMock::class.java)
    }
}

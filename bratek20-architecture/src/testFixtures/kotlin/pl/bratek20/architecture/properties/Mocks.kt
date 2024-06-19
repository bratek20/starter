package pl.bratek20.architecture.properties

import pl.bratek20.architecture.context.api.ContextBuilder
import pl.bratek20.architecture.context.api.ContextModule
import pl.bratek20.architecture.properties.api.MapPropertyKey
import pl.bratek20.architecture.properties.api.Properties
import pl.bratek20.architecture.properties.api.TypedPropertyKey

class PropertiesMock : Properties {
    private val values: MutableMap<TypedPropertyKey<*>, Any> = mutableMapOf()

    override fun <T : Any> get(key: TypedPropertyKey<T>): T {
        return values[key] as T
    }

    override fun <Id : Any, E : Any> findElement(key: MapPropertyKey<Id, E>, id: Id): E? {
        val list = get(key)
        return list.firstOrNull { key.idProvider(it) == id }
    }

    fun setProperty(key: TypedPropertyKey<*>, value: Any) {
        values.computeIfAbsent(key) { value }
    }
}

class PropertiesMocks : ContextModule {
    override fun apply(builder: ContextBuilder) {
        builder.setImpl(Properties::class.java, PropertiesMock::class.java)
    }
}

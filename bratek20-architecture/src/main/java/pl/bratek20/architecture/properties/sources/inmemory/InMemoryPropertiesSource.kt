package pl.bratek20.architecture.properties.sources.inmemory

import pl.bratek20.architecture.context.api.ContextBuilder
import pl.bratek20.architecture.context.api.ContextModule
import pl.bratek20.architecture.properties.api.*
import pl.bratek20.architecture.serialization.api.SerializedValue
import pl.bratek20.architecture.serialization.impl.SerializerLogic
import kotlin.reflect.KClass

class InMemoryPropertiesSource(
    private val name: String
) : PropertiesSource {

    private val properties: MutableMap<TypedPropertyKey<*>, Any> = HashMap()

    fun set(key: TypedPropertyKey<*>, property: Any) {
        properties[key] = property
    }

    override fun getName(): PropertiesSourceName {
        return PropertiesSourceName(name)
    }

    override fun getAllKeys(): Set<String> {
        return properties.keys.map { it.name }.toSet()
    }

    override fun getValue(keyName: String): SerializedValue {
        val property = properties.keys.first { it.name == keyName }
        return SerializerLogic().serialize(properties[property]!!)
    }

    override fun <T : Any> isObjectOfType(keyName: String, type: KClass<T>): Boolean {
        val property = properties.keys.firstOrNull { it.name == keyName }
        return property != null && type.isInstance(properties[property])
    }

    override fun <T : Any> getList(key: ListPropertyKey<T>): List<T> {
        val property = properties.keys.firstOrNull { it.name == key.name }
        return properties[property] as List<T>
    }

    override fun <T : Any> getObject(key: ObjectPropertyKey<T>): T {
        val property = properties.keys.firstOrNull { it.name == key.name }
        return properties[property] as T
    }
}

class InMemoryPropertiesSourceImpl(
    private val name: String
): ContextModule {
    override fun apply(builder: ContextBuilder) {
        builder.addImplObject(PropertiesSource::class.java, InMemoryPropertiesSource(name))
    }
}

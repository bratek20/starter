package pl.bratek20.architecture.properties.impl

import pl.bratek20.architecture.exceptions.ApiException
import pl.bratek20.architecture.properties.api.*

class PropertyNotFoundException(message: String) : ApiException(message)

class PropertiesLogic(
    private val sources: Set<PropertiesSource>
) : Properties {

    override fun <T : Any> get(key: TypedPropertyKey<T>): T {
        val source = sources.firstOrNull { it.hasKey(key.name) }
        if (source == null) {
            throw PropertyNotFoundException(key.name)
        }

        if (key is ObjectPropertyKey<T>) {
            return source.getObject(key)
        }
        if (key is ListPropertyKey<*>) {
            return source.getList(key) as T
        }
        throw ApiException("Unknown key type")
    }
}

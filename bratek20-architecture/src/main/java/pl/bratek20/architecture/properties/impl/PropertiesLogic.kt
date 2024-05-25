package pl.bratek20.architecture.properties.impl

import pl.bratek20.architecture.exceptions.ApiException
import pl.bratek20.architecture.properties.api.*


class PropertiesLogic(
    private val sources: Set<PropertiesSource>
) : Properties {

    override fun <T : Any> get(key: TypedPropertyKey<T>): T {
        val source = sources.firstOrNull { it.hasKey(key.name) }
        if (source == null) {
            throw PropertyNotFoundException("Property `${key.name}` not found")
        }

        if (key is ObjectPropertyKey<T>) {
            if (source.isListWithWrappedType(key.name, key.type)) {
                throw PropertyKeyTypeException("Property `${key.name}` is a list but was requested as object")
            }
            return source.getObject(key)
        }
        if (key is ListPropertyKey<*>) {
            if (source.isObjectOfType(key.name, key.elementType)) {
                throw PropertyKeyTypeException("Property `${key.name}` is an object but was requested as list")
            }
            return source.getList(key) as T
        }
        throw ApiException("Unknown key type")
    }
}

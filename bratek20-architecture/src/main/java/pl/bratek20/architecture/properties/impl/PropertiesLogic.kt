package pl.bratek20.architecture.properties.impl

import pl.bratek20.architecture.properties.api.*

class PropertiesLogic(
    private val sources: Set<PropertiesSource>
) : Properties {


    override fun <T : Any> get(key: TypedPropertyKey<T>): T {
        TODO("Not yet implemented")
    }
}

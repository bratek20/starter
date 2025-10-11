package com.github.bratek20.architecture.properties.api

interface Properties {
    fun <T: Any> get(key: PropertyKey<T>): T
    fun <T: Any> find(key: PropertyKey<T>): T?

    fun <Id: Any, E: Any> findElement(key: MapPropertyKey<Id, E>, id: Id): E?

    fun addSource(source: PropertiesSource)

    fun getAll(): List<Property>
}

package pl.bratek20.architecture.properties.api

interface Properties {
    fun <T: Any> get(key: TypedPropertyKey<T>): T
    fun <Id: Any, E: Any> findElement(key: MapPropertyKey<Id, E>, id: Id): E?
}

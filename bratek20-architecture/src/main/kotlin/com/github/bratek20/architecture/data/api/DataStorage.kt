package com.github.bratek20.architecture.data.api

interface DataStorage {
    fun <T: Any> set(key: DataKey<T>, value: T)
    fun <T: Any> find(key: DataKey<T>): T?
    fun <T: Any> get(key: DataKey<T>): T
    fun <T: Any> delete(key: DataKey<T>)

    fun <Id: Any, E: Any> addElement(key: MapDataKey<Id, E>, id: Id, value: E): Boolean
    fun <Id: Any, E: Any> findElement(key: MapDataKey<Id, E>, id: Id): E?
    fun <Id: Any, E: Any> getElement(key: MapDataKey<Id, E>, id: Id): E
}
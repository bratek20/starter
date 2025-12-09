package com.github.bratek20.architecture.data.api

interface DataStorage {
    fun <T: Any> set(key: DataKey<T>, value: T)
    fun <T: Any> find(key: DataKey<T>): T?
    fun <T: Any> get(key: DataKey<T>): T
    fun <T: Any> delete(key: DataKey<T>)

    fun <Id: Any, E: Any> addElement(key: MapDataKey<Id, E>, value: E): Boolean
    fun <Id: Any, E: Any> findElement(key: MapDataKey<Id, E>, id: Id): E?
    fun <Id: Any, E: Any> getElement(key: MapDataKey<Id, E>, id: Id): E

    fun <Id: Any, E: Any> getOrCreateElement(
        key: MapDataKey<Id, E>,
        id: Id,
        elementFactory: (id: Id) -> E
    ): E {
        var elem = this.findElement(key, id)
        if (elem == null) {
            if (this.find(key) == null) {
                this.set(key, mutableListOf())
            }
            this.addElement(key, elementFactory(id))
            elem = this.getElement(key, id)
        }
        return elem
    }

    fun <Id: Any, E: Any> upsertElement(
        key: MapDataKey<Id, E>,
        elem: E
    ) {
        val id = key.idProvider(elem)
        val list = this.find(key)?.toMutableList() ?: mutableListOf()
        val index = list.indexOfFirst { key.idProvider(it) == id }
        if (index >= 0) {
            list[index] = elem
        } else {
            list.add(elem)
        }
        this.set(key, list)
    }
}
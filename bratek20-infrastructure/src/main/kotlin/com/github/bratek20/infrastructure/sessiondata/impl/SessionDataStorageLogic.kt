package com.github.bratek20.infrastructure.sessiondata.impl

import com.github.bratek20.architecture.data.api.DataKey
import com.github.bratek20.architecture.data.api.DataStorage
import com.github.bratek20.architecture.data.api.MapDataKey
import com.github.bratek20.infrastructure.userauthserver.api.UserSession
import org.springframework.stereotype.Component

@Component
class SessionDataStorageLogic(
    private val appStorage: DataStorage,
    private val userSession: UserSession
): DataStorage {
    override fun <T : Any> set(key: DataKey<T>, value: T) {
        appStorage.set(toUserKey(key), value)
    }

    override fun <T : Any> find(key: DataKey<T>): T? {
        return appStorage.find(toUserKey(key))
    }

    override fun <T : Any> get(key: DataKey<T>): T {
        return appStorage.get(toUserKey(key))
    }

    override fun <T : Any> delete(key: DataKey<T>) {
        appStorage.delete(toUserKey(key))
    }

    override fun <Id : Any, E : Any> addElement(key: MapDataKey<Id, E>, value: E): Boolean {
        return appStorage.addElement(toUserKey(key), value)
    }

    override fun <Id : Any, E : Any> findElement(key: MapDataKey<Id, E>, id: Id): E? {
        return appStorage.findElement(toUserKey(key), id)
    }

    override fun <Id : Any, E : Any> getElement(key: MapDataKey<Id, E>, id: Id): E {
        return appStorage.getElement(toUserKey(key), id)
    }

    private fun <T: Any> toUserKey(key: DataKey<T>): DataKey<T> {
        return key.copy("user" + userSession.getUserId() + "." + key.name)
    }

    private fun <Id : Any, E : Any> toUserKey(key: MapDataKey<Id, E>): MapDataKey<Id, E> {
        return key.copy("user" + userSession.getUserId() + "." + key.name) as MapDataKey<Id, E>
    }
}
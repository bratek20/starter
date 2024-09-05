package com.github.bratek20.architecture.data.impl

import com.github.bratek20.architecture.data.api.*
import com.github.bratek20.architecture.serialization.api.SerializedValue
import com.github.bratek20.architecture.serialization.impl.SerializerLogic
import com.github.bratek20.architecture.storage.api.NotFoundInStorageException
import com.github.bratek20.architecture.storage.api.StorageKeyTypeException
import com.github.bratek20.architecture.storage.impl.StorageLogic

class DataStorageLogic(
    private val integration: DataStorageIntegration
) : DataStorage, StorageLogic() {

    override fun <T : Any> set(key: DataKey<T>, value: T) {
        integration.setValue(key.name, SerializerLogic().serialize(value))
    }

    override fun <T : Any> find(key: DataKey<T>): T? {
        return super.find(key)
    }

    override fun <T : Any> get(key: DataKey<T>): T {
        return super.get(key)
    }

    override fun <Id : Any, E : Any> addElement(key: MapDataKey<Id, E>, id: Id, value: E): Boolean {
        TODO("Not yet implemented")
    }

    override fun <Id : Any, E : Any> findElement(key: MapDataKey<Id, E>, id: Id): E? {
        return super.findElement(key, id)
    }

    override fun <Id : Any, E : Any> getElement(key: MapDataKey<Id, E>): E {
        TODO("Not yet implemented")
    }

    override fun findValue(keyName: String): SerializedValue? {
        return integration.findValue(keyName)
    }

    override fun storageElementName(): String {
        return "Data"
    }

    override fun notFoundException(message: String): NotFoundInStorageException {
        return DataNotFoundException(message)
    }

    override fun keyTypeException(message: String): StorageKeyTypeException {
        return DataKeyTypeException(message)
    }
}

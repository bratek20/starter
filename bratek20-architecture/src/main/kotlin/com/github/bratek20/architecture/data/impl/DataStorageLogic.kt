package com.github.bratek20.architecture.data.impl

import com.github.bratek20.architecture.data.api.*
import com.github.bratek20.architecture.serialization.api.SerializedValue
import com.github.bratek20.architecture.serialization.context.SerializationFactory
import com.github.bratek20.architecture.serialization.impl.SerializerLogic
import com.github.bratek20.architecture.storage.api.NotFoundInStorageException
import com.github.bratek20.architecture.storage.api.StorageElementNotFoundException
import com.github.bratek20.architecture.storage.api.StorageKeyTypeException
import com.github.bratek20.architecture.storage.impl.StorageLogic

class DataStorageLogic(
    private val integration: DataManipulator
) : DataStorage, StorageLogic() {

    override fun <T : Any> set(key: DataKey<T>, value: T) {
        integration.setValue(key.name, serializer.serialize(value))
    }

    override fun <T : Any> find(key: DataKey<T>): T? {
        return super.find(key)
    }

    override fun <T : Any> get(key: DataKey<T>): T {
        return super.get(key)
    }

    override fun <T : Any> delete(key: DataKey<T>) {
        integration.setValue(key.name, null)
    }

    override fun <Id : Any, E : Any> addElement(key: MapDataKey<Id, E>, value: E): Boolean {
        return super.addElement(key, value)
    }

    override fun <Id : Any, E : Any> findElement(key: MapDataKey<Id, E>, id: Id): E? {
        return super.findElement(key, id)
    }

    override fun <Id : Any, E : Any> getElement(key: MapDataKey<Id, E>, id: Id): E {
        return super.getElement(key, id)
    }

    override fun findValue(keyName: String): SerializedValue? {
        return integration.findValue(keyName)
    }

    override fun setValue(keyName: String, value: SerializedValue) {
        integration.setValue(keyName, value)
    }

    override fun storageEntityName(): String {
        return "Data"
    }

    override fun notFoundInStorageException(message: String): NotFoundInStorageException {
        return DataNotFoundException(message)
    }

    override fun elementNotFoundException(message: String): StorageElementNotFoundException {
        return DataElementNotFoundException(message)
    }

    override fun keyTypeException(message: String): StorageKeyTypeException {
        return DataKeyTypeException(message)
    }

    companion object {
        val serializer = SerializationFactory.createSerializer();
    }
}

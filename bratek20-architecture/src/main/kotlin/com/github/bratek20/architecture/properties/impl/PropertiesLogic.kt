package com.github.bratek20.architecture.properties.impl

import com.github.bratek20.architecture.properties.api.*
import com.github.bratek20.architecture.serialization.api.SerializedValue
import com.github.bratek20.architecture.storage.api.NotFoundInStorageException
import com.github.bratek20.architecture.storage.api.StorageElementNotFoundException
import com.github.bratek20.architecture.storage.api.StorageKeyTypeException
import com.github.bratek20.architecture.storage.impl.StorageLogic
import com.github.bratek20.architecture.structs.api.*

class PropertiesLogic(
    private val initialSources: Set<PropertiesSource>
) : Properties, StorageLogic() {
    private val addedSources = mutableListOf<PropertiesSource>()

    private val allSources: Set<PropertiesSource>
        get() = initialSources + addedSources

    private val cachedProperties = mutableMapOf<String, Any>()
    override fun <T : Any> get(key: PropertyKey<T>): T {
        findSourceWithKeyName(key.name)
            ?: throw PropertyNotFoundException("Property `${key.name}` not found, sources: ${allSources.map { it.getName().value }}")


        return cachedProperties.getOrPut(key.name) { super.get(key) } as T
    }

    override fun <Id : Any, E : Any> findElement(key: MapPropertyKey<Id, E>, id: Id): E? {
        val list = get(key)
        return list.firstOrNull { key.idProvider(it) == id }
    }

    override fun addSource(source: PropertiesSource) {
        addedSources.add(source)
    }

    override fun getAll(): List<Property> {
        return allSources.flatMap { source ->
            source.getAllKeys().map { keyName ->
                Property(
                    keyName,
                    asAnyStruct(source.getValue(keyName))
                )
            }
        }
    }

    private fun asAnyStruct(value: SerializedValue): AnyStruct {
        if (value.getValue().startsWith("{")) {
            return serializer.deserialize(value, Struct::class.java)
        }

        val list = StructList()
        val rawStructList = serializer.deserializeList(value, Struct::class.java)
        rawStructList.forEach { list.add(it) }
        return list
    }

    private fun findSourceWithKeyName(keyName: String): PropertiesSource? {
        return allSources.firstOrNull {
            it.getAllKeys().contains(keyName)
        }
    }

    override fun findValue(keyName: String): SerializedValue? {
        val source = findSourceWithKeyName(keyName)
        return source?.getValue(keyName)
    }

    override fun setValue(keyName: String, value: SerializedValue) {
        throw UnsupportedOperationException("Setting value is not supported for properties")
    }

    override fun storageEntityName(): String {
        return "Property"
    }

    override fun notFoundInStorageException(message: String): NotFoundInStorageException {
        return PropertyNotFoundException(message)
    }

    override fun elementNotFoundException(message: String): StorageElementNotFoundException {
        return PropertyElementNotFoundException(message)
    }

    override fun keyTypeException(message: String): StorageKeyTypeException {
        return PropertyKeyTypeException(message)
    }
}

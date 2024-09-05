package com.github.bratek20.architecture.properties.api

import com.github.bratek20.architecture.storage.api.NotFoundInStorageException
import com.github.bratek20.architecture.storage.api.StorageElementNotFoundException
import com.github.bratek20.architecture.storage.api.StorageKeyTypeException

class PropertyNotFoundException(message: String) : NotFoundInStorageException(message)
class PropertyElementNotFoundException(message: String) : StorageElementNotFoundException(message)
class PropertyKeyTypeException(message: String) : StorageKeyTypeException(message)
package com.github.bratek20.architecture.data.api

import com.github.bratek20.architecture.storage.api.NotFoundInStorageException
import com.github.bratek20.architecture.storage.api.StorageElementNotFoundException
import com.github.bratek20.architecture.storage.api.StorageKeyTypeException

class DataNotFoundException(message: String) : NotFoundInStorageException(message)

class DataElementNotFoundException(message: String) : StorageElementNotFoundException(message)

class DataKeyTypeException(message: String) : StorageKeyTypeException(message)
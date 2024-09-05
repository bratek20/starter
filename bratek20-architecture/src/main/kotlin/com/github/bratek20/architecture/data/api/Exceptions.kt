package com.github.bratek20.architecture.data.api

import com.github.bratek20.architecture.storage.api.NotFoundInStorageException
import com.github.bratek20.architecture.storage.api.StorageKeyTypeException

class DataNotFoundException(message: String) : NotFoundInStorageException(message)

class DataKeyTypeException(message: String) : StorageKeyTypeException(message)
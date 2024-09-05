package com.github.bratek20.architecture.storage.api

import com.github.bratek20.architecture.exceptions.ApiException

abstract class NotFoundInStorageException(message: String) : ApiException(message)

abstract class StorageKeyTypeException(message: String) : ApiException(message)
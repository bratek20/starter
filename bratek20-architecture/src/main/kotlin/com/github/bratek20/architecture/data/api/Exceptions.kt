package com.github.bratek20.architecture.data.api

import com.github.bratek20.architecture.exceptions.ApiException

class DataNotFoundException(message: String) : ApiException(message)

class DataKeyTypeException(message: String) : ApiException(message)
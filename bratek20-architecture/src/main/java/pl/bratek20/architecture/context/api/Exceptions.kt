package pl.bratek20.architecture.context.api

import pl.bratek20.architecture.exceptions.ApiException

class ClassNotFoundException(message: String)
    : ApiException(message)

class MultipleClassesFoundException(message: String)
    : ApiException(message)
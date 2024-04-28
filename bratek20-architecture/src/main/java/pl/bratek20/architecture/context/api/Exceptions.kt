package pl.bratek20.architecture.context.api

import pl.bratek20.architecture.exceptions.ApiException

class ClassNotFoundInContextException(message: String)
    : ApiException(message)

class MultipleClassesFoundInContextException(message: String)
    : ApiException(message)
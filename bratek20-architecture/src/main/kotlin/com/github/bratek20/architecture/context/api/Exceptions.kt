package com.github.bratek20.architecture.context.api

import com.github.bratek20.architecture.exceptions.ApiException

class ClassNotFoundInContextException(message: String)
    : ApiException(message)

class MultipleClassesFoundInContextException(message: String)
    : ApiException(message)

class DependentClassNotFoundInContextException(message: String)
    : ApiException(message)

class SetCalledForExpectedManyTypeException(message: String)
    : ApiException(message)
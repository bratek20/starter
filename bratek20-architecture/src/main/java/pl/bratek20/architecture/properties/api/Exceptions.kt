package pl.bratek20.architecture.properties.api

import pl.bratek20.architecture.exceptions.ApiException

class PropertyNotFoundException(message: String) : ApiException(message)

class PropertyKeyTypeException(message: String) : ApiException(message)
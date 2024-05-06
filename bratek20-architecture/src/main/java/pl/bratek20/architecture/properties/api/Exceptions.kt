package pl.bratek20.architecture.properties.api

import pl.bratek20.architecture.exceptions.ApiException

class PropertiesSourceNotFoundException(message: String) : ApiException(message)
package pl.bratek20.architecture.exceptions

open class ApiException(message: String? = null ) : RuntimeException(message ?: "An Api exception occurred")

class ShouldNeverHappenException(message: String? = null) : RuntimeException(message ?: "This should never happen")

package pl.bratek20.architecture.context.impl

import pl.bratek20.architecture.context.api.ClassNotFoundException
import pl.bratek20.architecture.context.api.Context
import pl.bratek20.architecture.context.api.MultipleClassesFoundException

abstract class AbstractContext: Context {
    override fun <T> get(type: Class<T>): T {
        val found = getMany(type)
        if (found.isEmpty()) {
            throw ClassNotFoundException("Class ${type.simpleName} not found in context")
        }
        if (found.size > 1) {
            throw MultipleClassesFoundException("Multiple classes found for ${type.simpleName} in context")
        }
        return found.first()
    }
}
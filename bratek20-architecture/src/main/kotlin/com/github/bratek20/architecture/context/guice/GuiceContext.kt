package com.github.bratek20.architecture.context.guice

import com.google.inject.*
import com.google.inject.util.Types
import com.github.bratek20.architecture.context.api.ClassNotFoundInContextException
import com.github.bratek20.architecture.context.api.Context
import com.github.bratek20.architecture.context.api.DependentClassNotFoundInContextException
import com.github.bratek20.architecture.context.api.MultipleClassesFoundInContextException

class GuiceContext(private val injector: Injector) : Context {
    override fun <T : Any> get(type: Class<T>): T {
        try {
            val x = injector.getInstance(Key.get(setOf(type)))
            if (x.size > 1) {
                throw MultipleClassesFoundInContextException("Multiple classes found for ${type.simpleName} in context")
            }
        } catch (e: ConfigurationException) {
            //do nothing
        }

        try {
            return injector.getInstance(type)
        } catch (e: ConfigurationException) {
            throw ClassNotFoundInContextException("Class ${type.simpleName} not found in context")
        }
    }

    override fun <T : Any> getMany(type: Class<T>): Set<T> {
        return injector.getInstance(Key.get(setOf(type)))
    }

    fun <T> setOf(type: Class<T>?): TypeLiteral<Set<T>> {
        return TypeLiteral.get(Types.setOf(type)) as TypeLiteral<Set<T>>
    }
}
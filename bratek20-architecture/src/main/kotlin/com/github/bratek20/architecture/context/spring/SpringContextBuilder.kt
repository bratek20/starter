package com.github.bratek20.architecture.context.spring

import org.springframework.beans.factory.UnsatisfiedDependencyException
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import com.github.bratek20.architecture.context.api.Context
import com.github.bratek20.architecture.context.api.ContextBuilder
import com.github.bratek20.architecture.context.api.DependentClassNotFoundInContextException
import com.github.bratek20.architecture.context.impl.AbstractContextBuilder
import java.util.*

class SpringContextBuilder: AbstractContextBuilder() {
    private val classes = mutableListOf<Class<*>>()
    private val objects = mutableListOf<Any>()

    override fun <T> setClass(type: Class<T>): SpringContextBuilder {
        classes.add(type)
        return this
    }

    override fun <T> addClass(type: Class<T>): SpringContextBuilder {
        classes.add(type)
        return this
    }

    override fun <I, T : I> setImpl(interfaceType: Class<I>, implementationType: Class<T>): SpringContextBuilder {
        classes.add(implementationType)
        return this
    }

    override fun <I, T : I> addImpl(interfaceType: Class<I>, implementationType: Class<T>): SpringContextBuilder {
        classes.add(implementationType)
        return this
    }

    override fun <I: Any, T : I> setImplObject(interfaceType: Class<I>, implementationObj: T): SpringContextBuilder {
        objects.add(implementationObj)
        return this
    }

    override fun <I: Any, T : I> addImplObject(interfaceType: Class<I>, implementationObj: T): SpringContextBuilder {
        objects.add(implementationObj)
        return this
    }

    override fun build(): SpringContext {
        val context = AnnotationConfigApplicationContext()
        classes.forEach {
            context.register(it)
        }
        objects.forEach {
            val suffix = UUID.randomUUID().toString()
            context.beanFactory.registerSingleton(it::class.java.name + suffix, it)
        }
        try {
            context.refresh()
        }
        catch (ex: UnsatisfiedDependencyException) {
            val msg = ex.message ?: "";
            val beanWithError = msg.substringAfterLast("Error creating bean with name '").substringBefore("'")
            val classWithError = beanWithError[0].uppercase() + beanWithError.substring(1)

            val fullDependentClassName = msg.substringAfter("No qualifying bean of type '").substringBefore("'")
            val dependentClass = fullDependentClassName.substringAfterLast(".")

            throw DependentClassNotFoundInContextException("Class $dependentClass needed by class $classWithError not found")
        }

        return SpringContext(context)
    }
}
package pl.bratek20.architecture.context.spring

import org.springframework.beans.factory.UnsatisfiedDependencyException
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import pl.bratek20.architecture.context.api.Context
import pl.bratek20.architecture.context.api.ContextBuilder
import pl.bratek20.architecture.context.api.DependentClassNotFoundInContextException
import pl.bratek20.architecture.context.impl.AbstractContextBuilder
import java.util.*

class SpringContextBuilder: AbstractContextBuilder() {
    private val classes = mutableListOf<Class<*>>()
    private val objects = mutableListOf<Any>()

    override fun <T> setClass(type: Class<T>): ContextBuilder {
        classes.add(type)
        return this
    }

    override fun <T> addClass(type: Class<T>): ContextBuilder {
        classes.add(type)
        return this
    }

    override fun <I, T : I> setImpl(interfaceType: Class<I>, implementationType: Class<T>): ContextBuilder {
        classes.add(implementationType)
        return this
    }

    override fun <I, T : I> addImpl(interfaceType: Class<I>, implementationType: Class<T>): ContextBuilder {
        classes.add(implementationType)
        return this
    }

    override fun setObject(obj: Any): ContextBuilder {
        objects.add(obj)
        return this
    }

    override fun addObject(obj: Any): ContextBuilder {
        objects.add(obj)
        return this
    }

    override fun build(): Context {
        val context = AnnotationConfigApplicationContext()
        classes.forEach { context.register(it) }
        objects.forEach {
            val suffix = UUID.randomUUID().toString()
            context.beanFactory.registerSingleton(it::class.java.name + suffix, it)
        }
        try {
            context.refresh()
        }
        catch (ex: UnsatisfiedDependencyException) {
            val msg = ex.message ?: "";
            val beanWithError = msg.substringAfter("Error creating bean with name '").substringBefore("'")
            val classWithError = beanWithError[0].uppercase() + beanWithError.substring(1)

            val fullDependentClassName = msg.substringAfter("No qualifying bean of type '").substringBefore("'")
            val dependentClass = fullDependentClassName.substringAfterLast(".")

            throw DependentClassNotFoundInContextException("Class $dependentClass needed by class $classWithError not found")
        }

        return SpringContext(context)
    }
}
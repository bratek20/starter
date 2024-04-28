package pl.bratek20.architecture.context.spring

import org.springframework.context.annotation.AnnotationConfigApplicationContext
import pl.bratek20.architecture.context.api.Context
import pl.bratek20.architecture.context.api.ContextBuilder
import pl.bratek20.architecture.context.impl.AbstractContextBuilder
import java.util.*

class SpringContextBuilder: AbstractContextBuilder() {
    private val classes = mutableListOf<Class<*>>()
    private val objects = mutableListOf<Any>()

    override fun withClass(type: Class<*>): ContextBuilder {
        classes.add(type)
        return this
    }

    override fun <I, T : I> bind(interfaceType: Class<I>, implementationType: Class<T>): ContextBuilder {
        classes.add(implementationType)
        return this
    }

    override fun withObject(obj: Any): ContextBuilder {
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
        context.refresh()
        return SpringContext(context)
    }
}
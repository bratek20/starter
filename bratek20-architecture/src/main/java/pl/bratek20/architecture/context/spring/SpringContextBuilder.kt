package pl.bratek20.architecture.context.spring

import pl.bratek20.architecture.context.api.Context
import pl.bratek20.architecture.context.api.ContextBuilder

class SpringContextBuilder: ContextBuilder {
    private val classes = mutableListOf<Class<*>>()

    override fun withClass(type: Class<*>): ContextBuilder {
        classes.add(type)
        return this
    }

    override fun <I, T : I> withBind(interfaceType: Class<I>, implementationType: Class<T>): ContextBuilder {
        classes.add(implementationType)
        return this
    }

    override fun build(): Context {
        return SpringContext(*classes.toTypedArray())
    }
}
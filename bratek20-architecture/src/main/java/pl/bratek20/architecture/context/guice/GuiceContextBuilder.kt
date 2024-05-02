package pl.bratek20.architecture.context.guice

import com.google.inject.AbstractModule
import com.google.inject.Guice
import com.google.inject.multibindings.Multibinder
import pl.bratek20.architecture.context.api.Context
import pl.bratek20.architecture.context.api.ContextBuilder
import pl.bratek20.architecture.context.impl.AbstractContextBuilder

private class MasterModule(
    private val classes: List<Class<*>>,
    private val binds: List<Pair<Class<*>, Class<*>>>,
    private val objects: List<Any>
) : AbstractModule() {
    override fun configure() {
        Multibinder.newSetBinder(binder(), Class::class.java).apply {
            classes.forEach {
                addBinding().toInstance(it)
            }
        }
        classes.forEach {
            bind(it)
        }

        binds.forEach { (interfaceType, implementationType) ->
            bind(interfaceType) to implementationType
        }

        objects.forEach {
            bind(it::class.java as Class<Any>).toInstance(it)
        }
    }
}

class GuiceContextBuilder : AbstractContextBuilder() {
    private val classes = mutableListOf<Class<*>>()
    private val binds = mutableListOf<Pair<Class<*>, Class<*>>>()
    private val objects = mutableListOf<Any>()

    override fun withClass(type: Class<*>): ContextBuilder {
        classes.add(type)
        return this
    }

    override fun <I, T : I> bind(interfaceType: Class<I>, implementationType: Class<T>): ContextBuilder {
        binds.add(interfaceType to implementationType)
        return this
    }

    override fun withObject(obj: Any): ContextBuilder {
        objects.add(obj)
        return this
    }

    override fun build(): Context {
        return GuiceContext(Guice.createInjector(
            MasterModule(
                classes,
                binds,
                objects
            )
        ))
    }
}
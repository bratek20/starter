package pl.bratek20.architecture.context.guice

import com.google.inject.AbstractModule
import com.google.inject.Guice
import com.google.inject.multibindings.Multibinder
import pl.bratek20.architecture.context.api.Context
import pl.bratek20.architecture.context.api.ContextBuilder
import pl.bratek20.architecture.context.impl.AbstractContextBuilder

class GuiceContextBuilder: AbstractContextBuilder() {
    private val modules = mutableListOf<AbstractModule>()

    override fun withClass(type: Class<*>): ContextBuilder {
        modules.add(object: AbstractModule() {
            override fun configure() {
                bind(type)
            }
        })
        return this
    }

    override fun <I, T : I> bind(interfaceType: Class<I>, implementationType: Class<T>): ContextBuilder {
        modules.add(object: AbstractModule() {
            override fun configure() {
                val multibinder = Multibinder.newSetBinder(binder(), interfaceType)
                multibinder.addBinding().to(implementationType)
            }
        })
        return this
    }

    override fun withObject(obj: Any): ContextBuilder {
        modules.add(object: AbstractModule() {
            override fun configure() {
                //bind(obj::class.java as Class<Any>).toInstance(obj)
                val multibinder = Multibinder.newSetBinder(binder(), obj::class.java as Class<Any>)
                multibinder.addBinding().toInstance(obj)
            }
        })
        return this
    }

    override fun build(): Context {
        return GuiceContext(
            Guice.createInjector(*modules.toTypedArray())
        )
    }
}
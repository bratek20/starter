package pl.bratek20.architecture.context.guice

import com.google.inject.Injector
import pl.bratek20.architecture.context.api.Context

class GuiceContext(private val injector: Injector) : Context {
    override fun <T> get(type: Class<T>): T {
        return injector.getInstance(type)
    }
}
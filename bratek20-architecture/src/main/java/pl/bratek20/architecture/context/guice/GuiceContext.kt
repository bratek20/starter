package pl.bratek20.architecture.context.guice

import com.google.inject.Injector
import pl.bratek20.architecture.context.impl.AbstractContext

class GuiceContext(private val injector: Injector) : AbstractContext() {
    override fun <T> getMany(type: Class<T>): List<T> {
        return injector.allBindings.entries
            .filter { it.key.typeLiteral.rawType == type }
            .map { injector.getInstance(it.key) }
            .map { type.cast(it) }
    }
}
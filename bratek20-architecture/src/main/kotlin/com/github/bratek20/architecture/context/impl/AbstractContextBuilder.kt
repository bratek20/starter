package com.github.bratek20.architecture.context.impl

import com.github.bratek20.architecture.context.api.Context
import com.github.bratek20.architecture.context.api.ContextBuilder
import com.github.bratek20.architecture.context.api.ContextModule

abstract class AbstractContextBuilder: ContextBuilder {

    private val knownModules = mutableSetOf<Class<*>>()
    final override fun withModule(module: ContextModule): ContextBuilder {
        if (module::class.java in knownModules) {
            return this
        }
        knownModules.add(module::class.java)
        module.apply(this)
        return this
    }

    protected var parentContext: Context? = null
    final override fun withParent(parent: Context): ContextBuilder {
        this.parentContext = parent
        return this
    }

    override fun withDefaultModule(module: ContextModule): ContextBuilder {
        TODO("Not yet implemented")
    }
}
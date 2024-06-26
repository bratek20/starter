package com.github.bratek20.architecture.context.impl

import com.github.bratek20.architecture.context.api.ContextBuilder
import com.github.bratek20.architecture.context.api.ContextModule

abstract class AbstractContextBuilder: ContextBuilder {

    final override fun withModule(module: ContextModule): ContextBuilder {
        module.apply(this)
        return this
    }

    override fun withDefaultModule(module: ContextModule): ContextBuilder {
        TODO("Not yet implemented")
    }
}
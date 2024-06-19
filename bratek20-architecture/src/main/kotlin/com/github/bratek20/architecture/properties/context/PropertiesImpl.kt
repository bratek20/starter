package com.github.bratek20.architecture.properties.context

import com.github.bratek20.architecture.context.api.ContextBuilder
import com.github.bratek20.architecture.context.api.ContextModule
import com.github.bratek20.architecture.properties.api.Properties
import com.github.bratek20.architecture.properties.impl.PropertiesLogic

class PropertiesImpl: ContextModule {
    override fun apply(builder: ContextBuilder) {
        builder.setImpl(Properties::class.java, PropertiesLogic::class.java)
    }
}
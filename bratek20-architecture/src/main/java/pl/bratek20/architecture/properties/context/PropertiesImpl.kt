package pl.bratek20.architecture.properties.context

import pl.bratek20.architecture.context.api.ContextBuilder
import pl.bratek20.architecture.context.api.ContextModule
import pl.bratek20.architecture.properties.api.Properties
import pl.bratek20.architecture.properties.impl.PropertiesLogic

class PropertiesImpl: ContextModule {
    override fun apply(builder: ContextBuilder) {
        builder.setImpl(Properties::class.java, PropertiesLogic::class.java)
    }
}
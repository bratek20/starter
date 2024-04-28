package pl.bratek20.architecture.events.impl

import pl.bratek20.architecture.context.api.ContextBuilder
import pl.bratek20.architecture.context.api.ContextModule

class EventsModule: ContextModule {
    override fun apply(builder: ContextBuilder) {
        builder.withClass(EventsLogic::class.java)
    }
}
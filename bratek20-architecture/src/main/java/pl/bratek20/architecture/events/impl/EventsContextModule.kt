package pl.bratek20.architecture.events.impl

import pl.bratek20.architecture.context.api.ContextBuilder
import pl.bratek20.architecture.context.api.ContextModule
import pl.bratek20.architecture.events.api.EventPublisher

class EventsContextModule: ContextModule {
    override fun apply(builder: ContextBuilder) {
        builder.setImpl(EventPublisher::class.java, EventsLogic::class.java)
    }
}
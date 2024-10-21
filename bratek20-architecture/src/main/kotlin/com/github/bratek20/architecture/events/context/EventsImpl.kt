package com.github.bratek20.architecture.events.context

import com.github.bratek20.architecture.context.api.ContextBuilder
import com.github.bratek20.architecture.context.api.ContextModule
import com.github.bratek20.architecture.events.api.EventPublisher
import com.github.bratek20.architecture.events.impl.EventPublisherLogic

class EventsImpl: ContextModule {
    override fun apply(builder: ContextBuilder) {
        builder.setImpl(EventPublisher::class.java, EventPublisherLogic::class.java)
    }
}
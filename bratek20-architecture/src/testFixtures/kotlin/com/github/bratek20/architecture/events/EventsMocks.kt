package com.github.bratek20.architecture.events

import com.github.bratek20.architecture.context.api.ContextBuilder
import com.github.bratek20.architecture.context.api.ContextModule
import com.github.bratek20.architecture.events.api.EventPublisher

class EventsMocks : ContextModule {
    override fun apply(builder: ContextBuilder) {
        builder.setImpl(EventPublisher::class.java, EventPublisherMock::class.java)
    }
}

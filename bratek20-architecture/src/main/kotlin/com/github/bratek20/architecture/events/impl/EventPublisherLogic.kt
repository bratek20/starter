package com.github.bratek20.architecture.events.impl

import com.github.bratek20.architecture.events.api.Event
import com.github.bratek20.architecture.events.api.EventPublisher
import com.google.common.eventbus.EventBus
import com.google.common.eventbus.Subscribe

class EventPublisherLogic: EventPublisher {
    private val eventBus = EventBus()

    override fun publish(event: Event) {
        eventBus.post(event)
    }

    override fun <T : Event> addListener(listener: (T) -> Unit) {
        val parameterType = listener::class.java.methods
            .firstOrNull { it.name == "invoke" }
            ?.parameters?.firstOrNull()?.type

        if (parameterType == null) {
            throw IllegalArgumentException("Calculating parameter type failed for listener")
        }

        eventBus.register(object : Any() {
            @Subscribe
            fun handleEvent(event: Any) {
                if (parameterType.isInstance(event)) {
                    @Suppress("UNCHECKED_CAST")
                    listener(event as T)
                }
            }
        })
    }
}

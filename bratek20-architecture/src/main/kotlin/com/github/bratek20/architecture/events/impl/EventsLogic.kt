package com.github.bratek20.architecture.events.impl

import com.github.bratek20.architecture.events.api.Event
import com.github.bratek20.architecture.events.api.EventListener
import com.github.bratek20.architecture.events.api.EventPublisher
import com.google.common.eventbus.EventBus
import com.google.common.eventbus.Subscribe
import java.lang.reflect.ParameterizedType
import java.util.function.Consumer

class EventsLogic(listeners: Set<EventListener<*>>) : EventPublisher {
    private val eventBus = EventBus()

    init {
        listeners.forEach(Consumer { listener: EventListener<*> -> this.subscribe(listener) })
    }

    override fun publish(event: Event?) {
        eventBus.post(event)
    }


    private fun <T : Event?> subscribe(listener: EventListener<T>) {
        // Reflection to find the actual event type handled by the listener
        val type = listener.javaClass.genericInterfaces[0] as ParameterizedType
        val eventType = type.actualTypeArguments[0] as Class<T>

        // Register a wrapper that only forwards the correct event types
        eventBus.register(object : Any() {
            @Subscribe
            fun handleEvent(event: Any?) {
                if (eventType.isInstance(event)) {
                    listener.handleEvent(eventType.cast(event))
                }
            }
        })
    }
}

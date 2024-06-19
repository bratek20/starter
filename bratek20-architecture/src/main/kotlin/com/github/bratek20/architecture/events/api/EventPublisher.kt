package com.github.bratek20.architecture.events.api

interface EventPublisher {
    fun publish(event: Event?)
}

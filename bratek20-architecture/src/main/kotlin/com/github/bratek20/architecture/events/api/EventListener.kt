package com.github.bratek20.architecture.events.api

interface EventListener<T : Event> {
    fun handleEvent(event: T)
}

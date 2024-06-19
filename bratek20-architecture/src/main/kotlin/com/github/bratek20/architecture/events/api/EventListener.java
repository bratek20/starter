package com.github.bratek20.architecture.events.api;

public interface EventListener<T extends Event> {
    void handleEvent(T event);
}

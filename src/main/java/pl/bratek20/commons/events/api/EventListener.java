package pl.bratek20.commons.events.api;

public interface EventListener<T extends Event> {
    void handleEvent(T event);
}

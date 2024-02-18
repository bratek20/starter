package pl.bratek20.architecture.events.api;

public interface EventsApi {
    void publish(Event event);

    <T extends Event> void subscribe(Class<T> eventType, EventListener<T> listener);
}

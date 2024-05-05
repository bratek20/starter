package pl.bratek20.architecture.events.api;

public interface EventPublisher {
    void publish(Event event);
}

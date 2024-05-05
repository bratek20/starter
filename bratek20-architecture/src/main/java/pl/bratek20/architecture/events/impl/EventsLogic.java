package pl.bratek20.architecture.events.impl;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import pl.bratek20.architecture.events.api.Event;
import pl.bratek20.architecture.events.api.EventListener;
import pl.bratek20.architecture.events.api.EventPublisher;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Set;

public class EventsLogic implements EventPublisher {
    private final EventBus eventBus = new EventBus();

    public EventsLogic(Set<EventListener> listeners) {
        listeners.forEach(this::subscribe);
    }

    @Override
    public void publish(Event event) {
        eventBus.post(event);
    }


    private <T extends Event> void subscribe(EventListener<T> listener) {
        // Reflection to find the actual event type handled by the listener
        ParameterizedType type = (ParameterizedType) listener.getClass().getGenericInterfaces()[0];
        Class<T> eventType = (Class<T>) type.getActualTypeArguments()[0];

        // Register a wrapper that only forwards the correct event types
        eventBus.register(new Object() {
            @Subscribe
            public void handleEvent(Object event) {
                if (eventType.isInstance(event)) {
                    listener.handleEvent(eventType.cast(event));
                }
            }
        });
    }
}

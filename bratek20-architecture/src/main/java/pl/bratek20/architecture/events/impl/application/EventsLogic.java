package pl.bratek20.architecture.events.impl.application;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import pl.bratek20.architecture.events.api.Event;
import pl.bratek20.architecture.events.api.EventListener;
import pl.bratek20.architecture.events.api.EventPublisher;

import java.util.List;

public class EventsLogic implements EventPublisher {
    private final EventBus eventBus;

    public EventsLogic(EventBus eventBus, List<EventListener<?>> listeners) {
        this.eventBus = eventBus;
        listeners.forEach(this::subscribe);
    }

    @Override
    public void publish(Event event) {
        eventBus.post(event);
    }

    private <T extends Event> void subscribe(EventListener<T> listener) {
        class EventListenerWrapper {
            @Subscribe
            public void handleEvent(T event) {
                listener.handleEvent(event);
            }
        }
        eventBus.register(new EventListenerWrapper());
    }
}

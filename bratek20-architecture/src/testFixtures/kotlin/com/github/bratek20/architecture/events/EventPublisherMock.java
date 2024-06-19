package com.github.bratek20.architecture.events;

import com.github.bratek20.architecture.events.api.Event;
import com.github.bratek20.architecture.events.api.EventListener;
import com.github.bratek20.architecture.events.api.EventPublisher;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class EventPublisherMock implements EventPublisher {
    private final List<Event> publishedEvents = new ArrayList<>();

    @Override
    public void publish(Event event) {
        publishedEvents.add(event);
    }

    public void assertOneEventPublished(Event event) {
        assertThat(publishedEvents).containsExactly(event);
    }

    public void reset() {
        publishedEvents.clear();
    }
}

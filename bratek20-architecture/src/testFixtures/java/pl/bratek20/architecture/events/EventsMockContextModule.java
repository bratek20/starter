package pl.bratek20.architecture.events;

import pl.bratek20.architecture.context.api.ContextBuilder;
import pl.bratek20.architecture.context.api.ContextModule;
import pl.bratek20.architecture.events.api.EventPublisher;

public class EventsMockContextModule implements ContextModule {

    @Override
    public void apply(ContextBuilder builder) {
        builder.bind(EventPublisher.class, EventPublisherMock.class);
    }
}

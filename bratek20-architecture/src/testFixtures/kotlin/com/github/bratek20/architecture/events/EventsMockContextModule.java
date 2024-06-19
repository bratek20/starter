package com.github.bratek20.architecture.events;

import com.github.bratek20.architecture.context.api.ContextBuilder;
import com.github.bratek20.architecture.context.api.ContextModule;
import com.github.bratek20.architecture.events.api.EventPublisher;

public class EventsMockContextModule implements ContextModule {

    @Override
    public void apply(ContextBuilder builder) {
        builder.setImpl(EventPublisher.class, EventPublisherMock.class);
    }
}

package pl.bratek20.architecture.events.impl;

import pl.bratek20.architecture.events.api.EventPublisher;
import pl.bratek20.architecture.events.api.EventPublisherTest;
import pl.bratek20.architecture.events.impl.infrastructure.EventsConfig;
import pl.bratek20.spring.context.SpringContextBuilder;

public class EventsImplTest extends EventPublisherTest {

    @Override
    protected EventPublisher createContext(TestEventListener params) {
        return new SpringContextBuilder(EventsConfig.class)
            .registerSingleton("listener", params)
            .build()
            .get(EventPublisher.class);
    }
}

package pl.bratek20.architecture.events.impl;

import pl.bratek20.architecture.events.api.EventListener;
import pl.bratek20.architecture.events.api.EventPublisher;
import pl.bratek20.architecture.events.api.EventPublisherTest;
import pl.bratek20.architecture.events.impl.infrastructure.EventsConfig;
import pl.bratek20.spring.context.SpringContextBuilder;

import java.util.List;

public class EventsImplTest extends EventPublisherTest {

    private int counter = 0;
    private String getNextName() {
        return "listener" + counter++;
    }

    @Override
    protected EventPublisher createContext(List<EventListener<?>> params) {
        var builder = new SpringContextBuilder(EventsConfig.class);
        params.forEach(listener -> builder.registerSingleton(getNextName(), listener));
        return builder.build().get(EventPublisher.class);
    }
}

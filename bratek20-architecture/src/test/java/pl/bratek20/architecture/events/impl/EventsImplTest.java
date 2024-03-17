package pl.bratek20.architecture.events.impl;

import pl.bratek20.architecture.events.api.EventsApi;
import pl.bratek20.architecture.events.api.EventsApiTest;
import pl.bratek20.architecture.events.impl.infrastructure.EventsConfig;
import pl.bratek20.spring.context.SpringContextBuilder;

public class EventsImplTest extends EventsApiTest {

    @Override
    protected EventsApi createApi() {
        return new SpringContextBuilder(EventsConfig.class)
            .build()
            .get(EventsApi.class);
    }
}

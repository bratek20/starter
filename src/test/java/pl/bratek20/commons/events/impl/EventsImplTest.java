package pl.bratek20.commons.events.impl;

import pl.bratek20.commons.events.api.EventsApi;
import pl.bratek20.commons.events.api.EventsApiTest;
import pl.bratek20.commons.events.impl.infrastructure.EventsConfig;
import pl.bratek20.commons.spring.di.SpringContextBuilder;

public class EventsImplTest extends EventsApiTest {

    @Override
    protected EventsApi createApi() {
        return new SpringContextBuilder(EventsConfig.class)
            .build()
            .get(EventsApi.class);
    }
}

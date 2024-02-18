package pl.bratek20.architecture.events;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.bratek20.architecture.events.api.EventsApi;

@Configuration
public class TestEventsConfig {
    @Bean
    public EventsApi eventsApi() {
        return new EventsApiMock();
    }
}

package pl.bratek20.architecture.events;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.bratek20.architecture.events.api.EventPublisher;

@Configuration
public class TestEventsConfig {
    @Bean
    public EventPublisher eventsApi() {
        return new EventPublisherMock();
    }
}

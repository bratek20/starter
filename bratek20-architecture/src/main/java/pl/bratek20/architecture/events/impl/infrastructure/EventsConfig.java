package pl.bratek20.architecture.events.impl.infrastructure;

import com.google.common.eventbus.EventBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.bratek20.architecture.events.api.EventListener;
import pl.bratek20.architecture.events.api.EventPublisher;
import pl.bratek20.architecture.events.impl.application.EventsLogic;

import java.util.List;

@Configuration
public class EventsConfig {
    @Bean
    public EventBus eventBus() {
        return new EventBus();
    }

    @Bean
    public EventPublisher eventsApi(EventBus eventBus, List<EventListener<?>> listeners) {
        return new EventsLogic(eventBus, listeners);
    }
}

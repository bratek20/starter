package pl.bratek20.architecture.events.api;

import lombok.Value;
import org.junit.jupiter.api.Test;
import pl.bratek20.architecture.tests.ApiTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class EventsApiTest extends ApiTest<EventsApi> {

    @Value
    class TestEvent implements Event {
        int value;
    }

    @Value
    class TestEventListener implements EventListener<TestEvent> {
        List<TestEvent> events = new ArrayList<>();

        @Override
        public void handleEvent(TestEvent event) {
            events.add(event);
        }
    }
    @Test
    void shouldWork() {
        // given
        var listener = new TestEventListener();
        api.subscribe(TestEvent.class, listener);

        // when
        api.publish(new TestEvent(1));
        api.publish(new TestEvent(2));

        // then
        assertThat(listener.events)
            .containsExactly(
                new TestEvent(1),
                new TestEvent(2)
            );
    }
}
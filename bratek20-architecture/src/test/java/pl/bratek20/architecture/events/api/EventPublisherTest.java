package pl.bratek20.architecture.events.api;

import lombok.Value;
import org.junit.jupiter.api.Test;
import pl.bratek20.tests.ParamsContextTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class EventPublisherTest extends ParamsContextTest<EventPublisherTest.TestEventListener, EventPublisher> {

    @Value
    public static class TestEvent implements Event {
        int value;
    }

    @Value
    public class TestEventListener implements EventListener<TestEvent> {
        List<TestEvent> events = new ArrayList<>();

        @Override
        public void handleEvent(TestEvent event) {
            events.add(event);
        }

        @Override
        public Class<TestEvent> getEventType() {
            return TestEvent.class;
        }
    }

    private TestEventListener listener;
    private EventPublisher api;

    @Override
    protected TestEventListener defaultParams() {
        listener = new TestEventListener();
        return listener;
    }

    @Override
    protected void applyContext(EventPublisher context) {
        api = context;
    }

    @Test
    void shouldWork() {
        // given

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
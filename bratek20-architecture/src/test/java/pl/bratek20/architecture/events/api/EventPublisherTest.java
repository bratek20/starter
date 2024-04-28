package pl.bratek20.architecture.events.api;

import lombok.Value;
import org.junit.jupiter.api.Test;
import pl.bratek20.tests.ParamsContextTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class EventPublisherTest extends ParamsContextTest<List<EventListener<?>>, EventPublisher> {

    @Value
    public static class TestEvent implements Event {
        int value;
    }

    @Value
    public static class OtherEvent implements Event {
        int value;
    }

    @Value
    public class TestEventListener implements EventListener<TestEvent> {
        List<TestEvent> events = new ArrayList<>();

        @Override
        public void handleEvent(TestEvent event) {
            events.add(event);
        }
    }

    @Value
    public class OtherEventListener implements EventListener<OtherEvent> {
        List<OtherEvent> events = new ArrayList<>();

        @Override
        public void handleEvent(OtherEvent event) {
            events.add(event);
        }
    }

    private TestEventListener listener1;
    private TestEventListener listener2;
    private OtherEventListener otherListener;
    private EventPublisher api;

    @Override
    protected List<EventListener<?>> defaultParams() {
        listener1 = new TestEventListener();
        listener2 = new TestEventListener();
        otherListener = new OtherEventListener();
        return List.of(listener1, listener2, otherListener);
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
        api.publish(new OtherEvent(3));

        // then
        assertThat(listener1.events)
            .containsExactly(
                new TestEvent(1),
                new TestEvent(2)
            );
        assertThat(listener2.events)
            .containsExactly(
                new TestEvent(1),
                new TestEvent(2)
            );
        assertThat(otherListener.events)
            .containsExactly(
                new OtherEvent(3)
            );
    }
}
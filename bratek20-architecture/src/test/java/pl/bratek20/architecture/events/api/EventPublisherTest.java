package pl.bratek20.architecture.events.api;

import lombok.Value;
import org.junit.jupiter.api.Test;
import pl.bratek20.architecture.context.spring.SpringContextBuilder;
import pl.bratek20.architecture.events.impl.EventsContextModule;
import pl.bratek20.tests.InterfaceTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class EventPublisherTest extends InterfaceTest<EventPublisher>{

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

    @Override
    protected EventPublisher createInstance() {
        listener1 = new TestEventListener();
        listener2 = new TestEventListener();
        otherListener = new OtherEventListener();

        return new SpringContextBuilder()
            .setObject(listener1)
            .setObject(listener2)
            .setObject(otherListener)
            .withModule(new EventsContextModule())
            .build()
            .get(EventPublisher.class);
    }

    @Test
    void shouldWork() {
        // given

        // when
        instance.publish(new TestEvent(1));
        instance.publish(new TestEvent(2));
        instance.publish(new OtherEvent(3));

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
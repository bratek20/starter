package pl.bratek20.architecture.events.api;

import lombok.Value;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.MethodSource;
import pl.bratek20.architecture.context.api.ContextBuilder;
import pl.bratek20.architecture.context.guice.GuiceContextBuilder;
import pl.bratek20.architecture.context.spring.SpringContextBuilder;
import pl.bratek20.architecture.events.impl.EventsContextModule;
import pl.bratek20.tests.InterfaceTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class EventPublisherTest {

    @Value
    public static class TestEvent implements Event {
        int value;
    }

    @Value
    public static class OtherEvent implements Event {
        int value;
    }

    @Value
    public static class TestEventListener implements EventListener<TestEvent> {
        List<TestEvent> events = new ArrayList<>();

        @Override
        public void handleEvent(TestEvent event) {
            events.add(event);
        }
    }

    @Value
    public static class OtherEventListener implements EventListener<OtherEvent> {
        List<OtherEvent> events = new ArrayList<>();

        @Override
        public void handleEvent(OtherEvent event) {
            events.add(event);
        }
    }

    static Stream<Arguments> provideArguments() {
        return Stream.of(
            Arguments.of("Spring", new SpringContextBuilder()),
            Arguments.of("Guice", new GuiceContextBuilder())
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideArguments")
    void shouldWork(String name, ContextBuilder builder) {
        // given
        var c = builder
            .addImpl(EventListener.class, TestEventListener.class)
            .addImpl(EventListener.class, OtherEventListener.class)
            .withModule(new EventsContextModule())
            .build();

        var listener = c.get(TestEventListener.class);
        var otherListener = c.get(OtherEventListener.class);

        var publisher = c.get(EventPublisher.class);

        // when
        publisher.publish(new TestEvent(1));
        publisher.publish(new TestEvent(2));
        publisher.publish(new OtherEvent(3));

        // then
        assertThat(listener.events)
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
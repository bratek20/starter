package pl.bratek20.architecture.events.api

import org.assertj.core.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import pl.bratek20.architecture.context.api.ContextBuilder
import pl.bratek20.architecture.context.guice.GuiceContextBuilder
import pl.bratek20.architecture.context.spring.SpringContextBuilder
import pl.bratek20.architecture.events.impl.EventsContextModule
import java.util.stream.Stream

class EventPublisherTest {
    data class TestEvent(
        val value: Int
    ) : Event


    data class OtherEvent(
        val value: Int
    ) : Event

    class TestEventListener : EventListener<TestEvent> {
        var events: MutableList<TestEvent> = ArrayList()

        override fun handleEvent(event: TestEvent) {
            events.add(event)
        }
    }

    class OtherEventListener : EventListener<OtherEvent> {
        var events: MutableList<OtherEvent> = ArrayList()

        override fun handleEvent(event: OtherEvent) {
            events.add(event)
        }
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideArguments")
    fun shouldWork(name: String?, builder: ContextBuilder) {
        // given
        val c = builder
            .addImpl(EventListener::class.java, TestEventListener::class.java)
            .addImpl(EventListener::class.java, OtherEventListener::class.java)
            .withModule(EventsContextModule())
            .build()

        val listeners = c.getMany(EventListener::class.java)

        val listener = listeners.find { l: EventListener<*> -> l is TestEventListener } as TestEventListener
        val otherListener = listeners.find { l: EventListener<*> -> l is OtherEventListener } as OtherEventListener

        val publisher = c.get(EventPublisher::class.java)

        // when
        publisher.publish(TestEvent(1))
        publisher.publish(TestEvent(2))
        publisher.publish(OtherEvent(3))

        // then
        Assertions.assertThat(listener.events)
            .containsExactly(
                TestEvent(1),
                TestEvent(2)
            )
        Assertions.assertThat(otherListener.events)
            .containsExactly(
                OtherEvent(3)
            )
    }

    companion object {
        @JvmStatic
        fun provideArguments(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("Spring", SpringContextBuilder()),
                Arguments.of("Guice", GuiceContextBuilder())
            )
        }
    }
}
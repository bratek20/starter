package pl.bratek20.architecture.events

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import pl.bratek20.architecture.context.someContextBuilder
import pl.bratek20.architecture.events.api.Event
import pl.bratek20.architecture.events.api.EventListener
import pl.bratek20.architecture.events.api.EventPublisher
import pl.bratek20.architecture.events.impl.EventsModule

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

    @Test
    fun shouldWork() {
        // given
        val c = someContextBuilder()
            .addImpl(EventListener::class.java, TestEventListener::class.java)
            .addImpl(EventListener::class.java, OtherEventListener::class.java)
            .withModule(EventsModule())
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
}
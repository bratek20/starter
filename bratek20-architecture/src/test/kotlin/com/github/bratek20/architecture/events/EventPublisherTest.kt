package com.github.bratek20.architecture.events

import com.github.bratek20.architecture.context.stableContextBuilder
import com.github.bratek20.architecture.events.api.Event
import com.github.bratek20.architecture.events.api.EventPublisher
import com.github.bratek20.architecture.events.context.EventsImpl
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class EventPublisherTest {
    data class TestEvent(
        val value: Int
    ) : Event


    data class OtherEvent(
        val value: Int
    ) : Event

    class TestEventListener(
        publisher: EventPublisher
    ) {
        init {
            publisher.addListener(this::handleEvent)
        }

        var events: MutableList<TestEvent> = ArrayList()

        private fun handleEvent(event: TestEvent) {
            events.add(event)
        }
    }

    class OtherEventListener(
        publisher: EventPublisher
    ) {
        init {
            publisher.addListener(this::handleEvent)
        }

        var events: MutableList<OtherEvent> = ArrayList()

        private fun handleEvent(event: OtherEvent) {
            events.add(event)
        }
    }

    @Test
    fun shouldWork() {
        // given
        val c = stableContextBuilder()
            .setClass(TestEventListener::class.java)
            .setClass(OtherEventListener::class.java)
            .withModule(EventsImpl())
            .build()

        val listener = c.get(TestEventListener::class.java)
        val otherListener = c.get(OtherEventListener::class.java)

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
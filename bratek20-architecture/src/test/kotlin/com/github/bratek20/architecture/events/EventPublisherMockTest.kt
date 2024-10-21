package com.github.bratek20.architecture.events

import com.github.bratek20.architecture.context.someContextBuilder
import com.github.bratek20.architecture.events.api.Event
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SomeEvent(
    val someField: String
): Event

class OtherEvent(
    val otherField: String
): Event

class EventPublisherMockTest {
    @Test
    fun shouldWork() {
        val mock = someContextBuilder()
            .withModules(
                EventsMocks()
            )
            .get(EventPublisherMock::class.java)

        mock.assertNoEventsPublished()

        mock.publish(SomeEvent("someValue"))

        mock.assertOneEventPublished<SomeEvent> {
            assertThat(it.someField).isEqualTo("someValue")
        }

        mock.publish(OtherEvent("otherValue"))

        mock.assertLastEventPublished<OtherEvent> {
            assertThat(it.otherField).isEqualTo("otherValue")
        }

        mock.assertPublishedEventsCount(2)

        mock.reset()

        mock.assertNoEventsPublished()
    }
}
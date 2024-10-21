package com.github.bratek20.architecture.events

import com.github.bratek20.architecture.events.api.Event
import com.github.bratek20.architecture.events.api.EventPublisher
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatCode

class EventPublisherMock : EventPublisher {
    val publishedEvents: MutableList<Event> = ArrayList()

    override fun publish(event: Event) {
        publishedEvents.add(event)
    }

    inline fun <reified T: Event> assertOneEventPublished(eventAssertion: (T) -> Unit) {
        assertPublishedEventsCount(1)
        assertLastEventPublished(eventAssertion)
    }

    inline fun <reified T: Event> assertLastEventPublished(eventAssertion: (T) -> Unit) {
        assertNthEventPublished<T>(publishedEvents.size - 1, eventAssertion)
    }

    inline fun <reified T: Event> assertNthEventPublished(n: Int, eventAssertion: (T) -> Unit) {
        assertThat(publishedEvents).hasSizeGreaterThan(n)
        assertThat(publishedEvents[n]).isInstanceOf(T::class.java)
        eventAssertion(publishedEvents[n] as T)
    }

    fun assertNoEventsPublished() {
        assertThat(publishedEvents).isEmpty()
    }

    fun assertPublishedEventsCount(count: Int) {
        assertThat(publishedEvents.size).isEqualTo(count)
    }

    fun reset() {
        publishedEvents.clear()
    }
}

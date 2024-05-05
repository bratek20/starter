package pl.bratek20.architecture.context.guice

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import pl.bratek20.architecture.context.api.A
import pl.bratek20.architecture.context.api.AImpl1
import pl.bratek20.architecture.context.api.ContextApiTest
import pl.bratek20.architecture.context.api.ContextBuilder


class GuiceContextImplTest: ContextApiTest() {
    override fun createInstance(): ContextBuilder {
        return GuiceContextBuilder()
    }
}
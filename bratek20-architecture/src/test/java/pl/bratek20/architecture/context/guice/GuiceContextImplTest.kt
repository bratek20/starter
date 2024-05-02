package pl.bratek20.architecture.context.guice

import pl.bratek20.architecture.context.api.ContextApiTest
import pl.bratek20.architecture.context.api.ContextBuilder

class GuiceContextImplTest: ContextApiTest() {
    override fun createInstance(): ContextBuilder {
        return GuiceContextBuilder()
    }
}
package pl.bratek20.architecture.context.spring

import pl.bratek20.architecture.context.api.ContextApiTest
import pl.bratek20.architecture.context.api.ContextBuilder

class SpringContextImplTest: ContextApiTest() {
    override fun createInstance(): ContextBuilder {
        return SpringContextBuilder()
    }
}
package pl.bratek20.architecture.context.guice

import com.google.inject.AbstractModule
import com.google.inject.Guice
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import pl.bratek20.architecture.context.api.ContextApiTest
import pl.bratek20.architecture.context.api.ContextBuilder
import pl.bratek20.architecture.context.api.X

class GuiceContextImplTest: ContextApiTest() {
    override fun createInstance(): ContextBuilder {
        return GuiceContextBuilder()
    }

    class OutsideWorldModule(
        private val mainModule: GuiceBuilderMainModule
    ): AbstractModule() {
        override fun configure() {
            install(mainModule)
        }
    }

    @Test
    fun `should create main module that can be used by outside world`() {
        val mainModule = GuiceContextBuilder()
            .setClass(X::class.java)
            .buildMainModule()

        val injector = Guice.createInjector(OutsideWorldModule(mainModule))

        val x = injector.getInstance(X::class.java)

        assertThat(x).isInstanceOf(X::class.java)
    }
}
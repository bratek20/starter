package com.github.bratek20.architecture.context.guice

import com.google.inject.AbstractModule
import com.google.inject.Guice
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import com.github.bratek20.architecture.context.api.ContextApiTest
import com.github.bratek20.architecture.context.api.ContextBuilder
import com.github.bratek20.architecture.context.api.SomeClass

class GuiceContextImplTest: ContextApiTest() {
    override fun createInstance(): ContextBuilder {
        return GuiceContextBuilder()
    }

    class LegacyModule(
        private val moduleForLegacy: GuiceBuilderMainModule
    ): AbstractModule() {
        override fun configure() {
            install(moduleForLegacy)
        }
    }

    @Test
    fun `should build module that can be used by legacy`() {
        val moduleForLegacy = GuiceContextBuilder()
            .setClass(SomeClass::class.java)
            .buildModuleForLegacy()

        val injector = Guice.createInjector(LegacyModule(moduleForLegacy))

        val x = injector.getInstance(SomeClass::class.java)

        assertThat(x).isInstanceOf(SomeClass::class.java)
    }
}
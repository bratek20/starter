package com.github.bratek20.architecture.context.spring

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import com.github.bratek20.architecture.context.api.ContextApiTest
import com.github.bratek20.architecture.context.api.ContextBuilder
import com.github.bratek20.architecture.context.api.X

class SpringContextImplTest: ContextApiTest() {
    override fun createInstance(): ContextBuilder {
        return SpringContextBuilder()
    }

    class MyBuilderProvider: SpringContextBuilderProvider {
        override fun provide(): SpringContextBuilder {
            return SpringContextBuilder()
                .setClass(X::class.java);
        }
    }

    @Configuration
    @Import(
        MyBuilderProvider::class,
        PostProcessorForLegacyConfig::class
    )
    open class OutsideWorldConfig

    @Test
    fun `should support connecting with legacy systems`() {
        val legacyContext = AnnotationConfigApplicationContext(OutsideWorldConfig::class.java)

        val x = legacyContext.getBean(X::class.java)

        assertThat(x).isInstanceOf(X::class.java)
    }
}
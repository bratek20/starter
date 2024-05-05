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

    @Test
    fun `BUG - should get impl classes that are the same`() {
        val c = createInstance()
            .addImpl(A::class.java, AImpl1::class.java)
            .setClass(As::class.java)
            .build()

        Assertions.assertThatCode { c.get(AImpl1::class.java) }.doesNotThrowAnyException()

        val aImplFromContext = c.get(AImpl1::class.java)
        val aImplFromSet = c.get(As::class.java).value.toList()[0]

        // BUG: they should be the same
        Assertions.assertThat(aImplFromContext).isNotEqualTo(aImplFromSet)
    }
}
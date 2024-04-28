package pl.bratek20.architecture.context.api

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import pl.bratek20.tests.InterfaceTest

abstract class ContextApiTest: InterfaceTest<ContextBuilder>() {
    class X
    interface A
    class AImpl1: A
    class AImpl2: A

    class SomeClass

    class SomeModuleContext: ContextModule {
        override fun apply(builder: ContextBuilder) {
            builder.withClass(SomeClass::class.java)
        }
    }


    class WithValue(val value: String)

    private fun context() = instance
        .withClass(X::class.java)
        .withBind(A::class.java, AImpl1::class.java)
        .withBind(A::class.java, AImpl2::class.java)
        .withModule(SomeModuleContext())
        .withObject(WithValue("value"))
        .withObject(WithValue("value2"))
        .build()

    @Test
    fun `should get class`() {
        val x = context().get(X::class.java)

        assertThat(x).isInstanceOf(X::class.java)
    }

    @Test
    fun `should get many classes`() {
        val a = context().getMany(A::class.java)

        assertThat(a).hasSize(2)
        assertThat(a[0]).isInstanceOf(AImpl1::class.java)
        assertThat(a[1]).isInstanceOf(AImpl2::class.java)
    }

    @Test
    fun `should get many objects`() {
        val result = context().getMany(WithValue::class.java)

        assertThat(result).hasSize(2)
        assertThat(result[0].value).isEqualTo("value")
        assertThat(result[1].value).isEqualTo("value2")
    }

    @Test
    fun `should get class from module`() {
        val someClass = context().get(SomeClass::class.java)

        assertThat(someClass).isInstanceOf(SomeClass::class.java)
    }

    @Test
    fun `should throw exception when class not found`() {
        assertThatThrownBy { context().get(String::class.java) }
            .isInstanceOf(ClassNotFoundInContextException::class.java)
            .hasMessage("Class String not found in context")
    }

    @Test
    fun `should throw exception when multiple classes found`() {
        assertThatThrownBy { context().get(A::class.java) }
            .isInstanceOf(MultipleClassesFoundInContextException::class.java)
            .hasMessage("Multiple classes found for A in context")
    }
}
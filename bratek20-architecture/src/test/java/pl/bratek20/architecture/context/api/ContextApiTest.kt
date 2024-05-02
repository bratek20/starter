package pl.bratek20.architecture.context.api

import com.google.inject.Inject
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import pl.bratek20.tests.InterfaceTest

class X
interface A
class AImpl1: A
class AImpl2: A

class SomeClass

class WithXClass @Inject constructor(
    val x: X
)

class SomeModuleContextModule: ContextModule {
    override fun apply(builder: ContextBuilder) {
        builder.withClass(SomeClass::class.java)
    }
}


class WithValue(val value: String)

abstract class ContextApiTest: InterfaceTest<ContextBuilder>() {

    @Test
    fun `should get class`() {
        val x = createInstance()
            .withClass(X::class.java)
            .build()
            .get(X::class.java)

        assertThat(x).isInstanceOf(X::class.java)
    }

    @Test
    fun `should get class that needs other class`() {
        val withX = createInstance()
            .withClass(X::class.java)
            .withClass(WithXClass::class.java)
            .build()
            .get(WithXClass::class.java)

        assertThat(withX).isInstanceOf(WithXClass::class.java)
        assertThat(withX.x).isNotNull()
    }

    class As @Inject constructor(
        val value: Set<A>
    )

    @Test
    fun `should inject classes list`() {
        val a = createInstance()
            .bind(A::class.java, AImpl1::class.java)
            .bind(A::class.java, AImpl2::class.java)
            .withClass(As::class.java)
            .build()
            .get(As::class.java).value.toList();

        assertThat(a).hasSize(2)
        assertThat(a[0]).isInstanceOf(AImpl1::class.java)
        assertThat(a[1]).isInstanceOf(AImpl2::class.java)
    }

    data class Values @Inject constructor(val value: Set<WithValue>)
    @Test
    fun `should get many objects`() {
        val result = createInstance()
            .withObject(WithValue("value"))
            .withObject(WithValue("value2"))
            .withClass(Values::class.java)
            .build()
            .get(Values::class.java).value.toList()

        assertThat(result).hasSize(2)
        assertThat(result[0].value).isEqualTo("value")
        assertThat(result[1].value).isEqualTo("value2")
    }

    @Test
    fun `should get class from module`() {
        val someClass = createInstance()
            .withModule(SomeModuleContextModule())
            .build()
            .get(SomeClass::class.java)

        assertThat(someClass).isInstanceOf(SomeClass::class.java)
    }

    @Test
    fun `should throw exception when class not found`() {
        assertThatThrownBy { createInstance().build().get(String::class.java) }
            .isInstanceOf(ClassNotFoundException::class.java)
            .hasMessage("Class String not found in context")
    }

    @Test
    fun `should throw exception when multiple classes found`() {
        assertThatThrownBy {
            createInstance()
                .withClass(AImpl1::class.java)
                .withClass(AImpl2::class.java)
                .build()
                .get(A::class.java)
        }
        .isInstanceOf(MultipleClassesFoundException::class.java)
        .hasMessage("Multiple classes found for A in context")
    }

    @Test
    fun `should throw exception if class to inject not found`() {
        assertThatThrownBy {
            createInstance()
                .withClass(WithXClass::class.java)
                .build()
        }
        .isInstanceOf(DependentClassNotFoundException::class.java)
        .hasMessage("Class X needed by class WithXClass not found")
    }
}
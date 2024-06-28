package com.github.bratek20.architecture.context.api

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class X
interface A
class AImpl1: A
class AImpl2: A

class SomeClass

class WithXClass(
    val x: X
)

class WithXClassSet(
    val x: Set<X>
)

class SomeModuleContextModule: ContextModule {
    override fun apply(builder: ContextBuilder) {
        builder.setClass(SomeClass::class.java)
    }
}

class WithValue(val value: String)

abstract class ContextApiTest {

    abstract fun createInstance(): ContextBuilder

    @Test
    fun `should get class`() {
        val x = createInstance()
            .setClass(X::class.java)
            .build()
            .get(X::class.java)

        assertThat(x).isInstanceOf(X::class.java)
    }

    @Test
    fun `should get class that needs other class`() {
        val withX = createInstance()
            .setClass(X::class.java)
            .setClass(WithXClass::class.java)
            .build()
            .get(WithXClass::class.java)

        assertThat(withX).isInstanceOf(WithXClass::class.java)
        assertThat(withX.x).isNotNull()
    }

    class As(
        val value: Set<A>
    )


    @Test
    fun `should get the same object for interface and impl (setImpl)`() {
        val c = createInstance()
            .setImpl(A::class.java, AImpl1::class.java)
            .build()

        val a = c.get(A::class.java)
        val aImpl = c.get(AImpl1::class.java)

        assertThat(a).isEqualTo(aImpl)
    }

    @Test
    fun `should get the same object for interface and impl (addImpl)`() {
        val c = createInstance()
            .addImpl(A::class.java, AImpl1::class.java)
            .build()

        val a = c.getMany(A::class.java).first()
        val aImpl = c.get(AImpl1::class.java)

        assertThat(a).isEqualTo(aImpl)
    }

    @Test
    fun `should inject classes set and get the same instances`() {
        val c = createInstance()
            .addImpl(A::class.java, AImpl1::class.java)
            .addImpl(A::class.java, AImpl2::class.java)
            .setClass(As::class.java)
            .build()

        val asValues = c.get(As::class.java).value.toList();

        assertThat(asValues).hasSize(2)
        assertThat(asValues[0]).isInstanceOf(AImpl1::class.java)
        assertThat(asValues[1]).isInstanceOf(AImpl2::class.java)

        val aInstances = c.getMany(A::class.java).toList()

        assertThat(aInstances).hasSize(2)
        assertThat(aInstances[0]).isInstanceOf(AImpl1::class.java)
        assertThat(aInstances[0]).isEqualTo(asValues[0])

        assertThat(aInstances[1]).isInstanceOf(AImpl2::class.java)
        assertThat(aInstances[1]).isEqualTo(asValues[1])
    }

    data class Values(val value: Set<WithValue>)

    @Test
    fun `should get many objects`() {
        val result = createInstance()
            .addImplObject(WithValue::class.java, WithValue("value"))
            .addImplObject(WithValue::class.java, WithValue("value2"))
            .setClass(Values::class.java)
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
            .isInstanceOf(ClassNotFoundInContextException::class.java)
            .hasMessage("Class String not found in context")
    }

    @Test
    fun `should throw exception when multiple classes found`() {
        assertThatThrownBy {
            createInstance()
                .addImpl(A::class.java, AImpl1::class.java)
                .addImpl(A::class.java, AImpl2::class.java)
                .build()
                .get(A::class.java)
        }
        .isInstanceOf(MultipleClassesFoundInContextException::class.java)
        .hasMessage("Multiple classes found for A in context")
    }

    @Test
    fun `should throw exception if class to inject not found`() {
        assertThatThrownBy {
            createInstance()
                .setClass(WithXClass::class.java)
                .build()
        }
        .isInstanceOf(DependentClassNotFoundInContextException::class.java)
        .hasMessage("Class X needed by class WithXClass not found")
    }

    @Disabled //TODO fix guice
    @Test
    fun `should inject empty set`() {
        val obj = createInstance()
            .setClass(WithXClassSet::class.java)
            .build()
            .get(WithXClassSet::class.java)

        assertThat(obj.x).isEmpty()
    }
}
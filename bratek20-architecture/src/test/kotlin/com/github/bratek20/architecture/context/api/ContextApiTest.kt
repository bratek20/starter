package com.github.bratek20.architecture.context.api

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class SomeClass

interface SomeInterface
class SomeInterfaceImpl1: SomeInterface
class SomeInterfaceImpl2: SomeInterface

interface OtherInterface
class OtherInterfaceImpl(
    val value: String
): OtherInterface

class WithSomeInterfaces(
    val someInterfaces: Set<SomeInterface>
)

class WithSomeClass(
    val someClass: SomeClass
)

class WithSomeClasses(
    val someClasses: Set<SomeClass>
)

class SomeModuleContextModule: ContextModule {
    override fun apply(builder: ContextBuilder) {
        builder.setClass(SomeClass::class.java)
    }
}

class ValueObject(val value: String)
data class WithValueObjects(val vos: Set<ValueObject>)

abstract class ContextApiTest {

    abstract fun createInstance(): ContextBuilder

    class AllSetObjects(
        val someClass: SomeClass,
        val someInterface: SomeInterface,
        val valueObject: ValueObject,
        val otherInterface: OtherInterface,
        //impls
        val someInterfaceImpl1: SomeInterfaceImpl1,
        val otherInterfaceImpl: OtherInterfaceImpl,
    )
    @Test
    fun `should support set operations, inject singletons and inject also impls`() {
        val context = createInstance()
            .setClass(SomeClass::class.java)
            .setImpl(SomeInterface::class.java, SomeInterfaceImpl1::class.java)
            .setObject(ValueObject("valueObject"))
            .setImplObject(OtherInterface::class.java, OtherInterfaceImpl("otherInterfaceImpl"))
            .setClass(AllSetObjects::class.java)
            .build()

        val allSetObjects = context.get(AllSetObjects::class.java)

        //checking singletons
        assertThat(allSetObjects.someClass).isSameAs(context.get(SomeClass::class.java))
        assertThat(allSetObjects.someInterface).isSameAs(context.get(SomeInterface::class.java))
        assertThat(allSetObjects.valueObject).isSameAs(context.get(ValueObject::class.java))
        assertThat(allSetObjects.otherInterface).isSameAs(context.get(OtherInterface::class.java))
        assertThat(allSetObjects.someInterfaceImpl1).isSameAs(context.get(SomeInterfaceImpl1::class.java))
        assertThat(allSetObjects.otherInterfaceImpl).isSameAs(context.get(OtherInterfaceImpl::class.java))

        //checking values just to be sure
        assertThat(allSetObjects.valueObject.value).isEqualTo("valueObject")
        assertThat(allSetObjects.otherInterfaceImpl.value).isEqualTo("otherInterfaceImpl")

        //checking impls
        assertThat(allSetObjects.someInterface).isSameAs(allSetObjects.someInterfaceImpl1)
        assertThat(allSetObjects.otherInterface).isSameAs(allSetObjects.otherInterfaceImpl)
    }

    @Test
    fun `should get class that needs other class`() {
        val withX = createInstance()
            .setClass(SomeClass::class.java)
            .setClass(WithSomeClass::class.java)
            .build()
            .get(WithSomeClass::class.java)

        assertThat(withX).isInstanceOf(WithSomeClass::class.java)
        assertThat(withX.someClass).isNotNull()
    }

    @Test
    fun `should get the same object for interface and impl (setImpl)`() {
        val c = createInstance()
            .setImpl(SomeInterface::class.java, SomeInterfaceImpl1::class.java)
            .build()

        val a = c.get(SomeInterface::class.java)
        val aImpl = c.get(SomeInterfaceImpl1::class.java)

        assertThat(a).isEqualTo(aImpl)
    }

    @Test
    fun `should get the same object for interface and impl (addImpl)`() {
        val c = createInstance()
            .addImpl(SomeInterface::class.java, SomeInterfaceImpl1::class.java)
            .build()

        val a = c.getMany(SomeInterface::class.java).first()
        val aImpl = c.get(SomeInterfaceImpl1::class.java)

        assertThat(a).isEqualTo(aImpl)
    }

    @Test
    fun `should inject classes set and get the same instances`() {
        val c = createInstance()
            .addImpl(SomeInterface::class.java, SomeInterfaceImpl1::class.java)
            .addImpl(SomeInterface::class.java, SomeInterfaceImpl2::class.java)
            .setClass(WithSomeInterfaces::class.java)
            .build()

        val asValues = c.get(WithSomeInterfaces::class.java).someInterfaces.toList();

        assertThat(asValues).hasSize(2)
        assertThat(asValues[0]).isInstanceOf(SomeInterfaceImpl1::class.java)
        assertThat(asValues[1]).isInstanceOf(SomeInterfaceImpl2::class.java)

        val aInstances = c.getMany(SomeInterface::class.java).toList()

        assertThat(aInstances).hasSize(2)
        assertThat(aInstances[0]).isInstanceOf(SomeInterfaceImpl1::class.java)
        assertThat(aInstances[0]).isEqualTo(asValues[0])

        assertThat(aInstances[1]).isInstanceOf(SomeInterfaceImpl2::class.java)
        assertThat(aInstances[1]).isEqualTo(asValues[1])
    }


    @Test
    fun `should get many objects`() {
        val result = createInstance()
            .addObject(ValueObject("value"))
            .addObject(ValueObject("value2"))
            .setClass(WithValueObjects::class.java)
            .build()
            .get(WithValueObjects::class.java).vos.toList()

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
                .addImpl(SomeInterface::class.java, SomeInterfaceImpl1::class.java)
                .addImpl(SomeInterface::class.java, SomeInterfaceImpl2::class.java)
                .build()
                .get(SomeInterface::class.java)
        }
        .isInstanceOf(MultipleClassesFoundInContextException::class.java)
        .hasMessage("Multiple classes found for SomeInterface in context")
    }

    @Test
    fun `should throw exception if class to inject not found`() {
        assertThatThrownBy {
            createInstance()
                .setClass(WithSomeClass::class.java)
                .build()
        }
        .isInstanceOf(DependentClassNotFoundInContextException::class.java)
        .hasMessage("Class SomeClass needed by class WithSomeClass not found")
    }

    @Disabled //TODO fix guice
    @Test
    fun `should inject empty set`() {
        val obj = createInstance()
            .setClass(WithSomeClasses::class.java)
            .build()
            .get(WithSomeClasses::class.java)

        assertThat(obj.someClasses).isEmpty()
    }
}
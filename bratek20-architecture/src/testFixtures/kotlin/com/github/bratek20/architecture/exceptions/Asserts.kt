package com.github.bratek20.architecture.exceptions

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import kotlin.reflect.KClass

data class ExpectedException(
    var type: KClass<out ApiException>? = null,
    var message: String? = null
)
fun assertApiExceptionThrown(block: () -> Unit, init: ExpectedException.() -> Unit) {
    val expected = ExpectedException().apply(init)

    val thrownException = shouldThrow<ApiException> {
        block()
    }

    if (expected.type != null) {
        thrownException::class shouldBe expected.type
    }
    if (expected.message != null) {
        thrownException.message shouldBe expected.message
    }
}
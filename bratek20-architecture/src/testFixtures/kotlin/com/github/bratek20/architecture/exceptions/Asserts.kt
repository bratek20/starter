package com.github.bratek20.architecture.exceptions

import io.kotest.assertions.asClue
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldStartWith
import kotlin.reflect.KClass

data class ExpectedException(
    var type: KClass<out ApiException>? = null,
    var message: String? = null,
    var messagePrefix: String? = null,
)
fun assertApiExceptionThrown(block: () -> Unit, init: ExpectedException.() -> Unit) {
    val expected = ExpectedException().apply(init)

    val thrownException = shouldThrow<Exception> {
        block()
    }

    "Checking exception ${thrownException.javaClass} with message: ${thrownException.message}".asClue {
        thrownException::class shouldBe ApiException::class
    }

    expected.type?.let {
        thrownException::class shouldBe it
    }
    expected.message?.let {
        thrownException.message shouldBe it
    }
    expected.messagePrefix?.let {
        thrownException.message shouldStartWith it
    }
}
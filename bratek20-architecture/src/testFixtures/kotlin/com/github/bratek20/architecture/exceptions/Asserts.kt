package com.github.bratek20.architecture.exceptions

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import kotlin.reflect.KClass

data class ExpectedException(
    var type: KClass<out ApiException>? = null,
    var message: String? = null,
    var messagePrefix: String? = null,
)

fun assertApiExceptionThrown(block: () -> Unit, init: ExpectedException.() -> Unit) {
    val expected = ExpectedException().apply(init)

    val thrownException = assertThatThrownBy {
        block()
    }.isInstanceOf(ApiException::class.java)

    expected.type?.let {
        thrownException.isInstanceOf(it.java)
    }
    expected.message?.let {
        thrownException.hasMessage(it)
    }
    expected.messagePrefix?.let {
        thrownException.hasMessageStartingWith(it)
    }
}
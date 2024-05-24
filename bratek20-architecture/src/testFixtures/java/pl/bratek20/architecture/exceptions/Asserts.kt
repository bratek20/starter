package pl.bratek20.architecture.exceptions

import org.assertj.core.api.Assertions.assertThatThrownBy
import kotlin.reflect.KClass

data class ExpectedException(
    var type: KClass<out ApiException>? = null,
    var message: String? = null
)
fun assertApiExceptionThrown(block: () -> Unit, init: ExpectedException.() -> Unit) {
    val expected = ExpectedException().apply(init)

    val x = assertThatThrownBy(block)
    if (expected.type != null) {
        x.isInstanceOf(expected.type?.java)
    }
    if (expected.message != null) {
        x.hasMessage(expected.message)
    }
}
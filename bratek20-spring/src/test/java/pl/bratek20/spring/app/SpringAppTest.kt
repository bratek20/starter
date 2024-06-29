package pl.bratek20.spring.app

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.context.annotation.Configuration

class SpringAppTest {

    @Test
    fun shouldStartAppWithoutWebServlet() {
        SpringApp.run()

        Assertions.assertThatCode { SpringApp.run() }
            .doesNotThrowAnyException()
    }
}
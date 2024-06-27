package pl.bratek20.spring.app;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThatCode;

class SpringAppTest {

    @Configuration
    static class EmptyConfig {
    }

    @Test
    void shouldStartAppWithoutWebServlet() {
        SpringApp.run(EmptyConfig.class, new String[]{});

        assertThatCode(() -> SpringApp.run(EmptyConfig.class, new String[]{}))
            .doesNotThrowAnyException();
    }
}
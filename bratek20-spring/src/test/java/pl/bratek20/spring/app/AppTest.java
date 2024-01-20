package pl.bratek20.spring.app;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThatCode;

class AppTest {

    @Configuration
    static class EmptyConfig {
    }

    @Test
    void shouldStartAppWithoutWebServlet() {
        App.run(EmptyConfig.class, new String[]{});

        assertThatCode(() -> App.run(EmptyConfig.class, new String[]{}))
            .doesNotThrowAnyException();
    }
}
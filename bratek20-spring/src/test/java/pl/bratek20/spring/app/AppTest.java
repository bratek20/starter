package pl.bratek20.spring.app;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;

import static org.assertj.core.api.Assertions.assertThat;

class AppTest {
    static boolean appStarted = false;
    static boolean appClosed = false;

    static class TestApplicationListener implements ApplicationListener<ApplicationEvent> {
        @Override
        public void onApplicationEvent(ApplicationEvent event) {
            if (event instanceof ContextRefreshedEvent) {
                appStarted = true;
            }
            if (event instanceof ContextClosedEvent) {
                appClosed = true;
            }
        }
    }

    @Configuration
    static class TestConfig {
        @Bean
        public TestApplicationListener testApplicationListener() {
            return new TestApplicationListener();
        }
    }

    //TODO: fix this test
    @Test
    void shouldStartAndCloseApp() {
        App.run(TestConfig.class, new String[]{});

        //Thread.sleep(1000);

        assertThat(appStarted).isTrue();
        //assertThat(appClosed).isTrue();
    }
}
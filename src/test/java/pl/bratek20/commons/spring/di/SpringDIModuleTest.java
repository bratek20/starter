package pl.bratek20.commons.spring.di;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

class SpringDIModuleTest {
    public record SomeClass(String someField) {}

    @Configuration
    public static class SomeConfig {
        @Bean
        public SomeClass someClass() {
            return new SomeClass("someValue");
        }
    }

    @Test
    void shouldBuildContextAndGetBean() {
        var context = new SpringContextBuilder(SomeConfig.class).build();
        var someClass = context.get(SomeClass.class);

        assertThat(someClass.someField).isEqualTo("someValue");
    }
}
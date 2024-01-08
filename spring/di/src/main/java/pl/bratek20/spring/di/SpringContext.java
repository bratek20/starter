package pl.bratek20.spring.di;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringContext {
    private final ConfigurableApplicationContext value;

    public SpringContext(ConfigurableApplicationContext value) {
        this.value = value;
    }

    public SpringContext(Class<?>... configurations) {
        this(new AnnotationConfigApplicationContext(configurations));
    }

    public <T> T get(Class<T> bean) {
        return value.getBean(bean);
    }
}

package pl.bratek20.commons.spring.di;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringContext {
    private final AnnotationConfigApplicationContext value;

    public SpringContext(AnnotationConfigApplicationContext value) {
        this.value = value;
    }

    public SpringContext(Class<?>... configurations) {
        this(new AnnotationConfigApplicationContext(configurations));
    }

    public <T> T get(Class<T> bean) {
        return value.getBean(bean);
    }
}

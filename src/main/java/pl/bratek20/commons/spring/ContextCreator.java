package pl.bratek20.commons.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ContextCreator {
    public static AnnotationConfigApplicationContext create(Class<?> configuration) {
        return new AnnotationConfigApplicationContext(configuration);
    }

    public static <T> T createAndGet(Class<?> configuration, Class<T> bean) {
        var context = create(configuration);
        return context.getBean(bean);
    }
}

package pl.bratek20.commons.spring;

import lombok.SneakyThrows;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class ContextHelper {
    private final List<Class<?>> configurations;

    record Singleton(String name, Object object) {}
    private final List<Singleton> singletonsToRegister = new ArrayList<>();

    private AnnotationConfigApplicationContext context;

    public ContextHelper(Class<?>... configurations) {
        this.configurations = List.of(configurations);
    }

    public ContextHelper build() {
        ensureBuilt();
        return this;
    }

    public <T> T get(Class<T> bean) {
        ensureBuilt();
        return context.getBean(bean);
    }

    private void ensureBuilt() {
        if (context == null) {
            context = new AnnotationConfigApplicationContext();
            singletonsToRegister.forEach(singleton -> context.getBeanFactory().registerSingleton(singleton.name(), singleton.object()));
            context.register(configurations.toArray(Class[]::new));
            context.refresh();
        }
    }

    public ContextHelper registerSingleton(String singletonName, Object singletonObject) {
        singletonsToRegister.add(new Singleton(singletonName, singletonObject));
        return this;
    }

    @SneakyThrows
    public ContextHelper registerSingletonClass(Class<?> singletonClass) {
        var singletonObject = singletonClass.getConstructor().newInstance();
        singletonsToRegister.add(new Singleton(singletonClass.getSimpleName(), singletonObject));
        return this;
    }
}

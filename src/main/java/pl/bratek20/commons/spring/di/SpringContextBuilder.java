package pl.bratek20.commons.spring.di;

import lombok.SneakyThrows;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class SpringContextBuilder {
    private final List<Class<?>> configurations;

    record Singleton(String name, Object object) {}
    private final List<Singleton> singletonsToRegister = new ArrayList<>();

    public SpringContextBuilder(Class<?>... configurations) {
        this.configurations = List.of(configurations);
    }

    public SpringContextBuilder registerSingleton(String singletonName, Object singletonObject) {
        singletonsToRegister.add(new Singleton(singletonName, singletonObject));
        return this;
    }

    @SneakyThrows
    public SpringContextBuilder registerSingletonClass(Class<?> singletonClass) {
        var singletonObject = singletonClass.getConstructor().newInstance();
        singletonsToRegister.add(new Singleton(singletonClass.getSimpleName(), singletonObject));
        return this;
    }

    public SpringContext build() {
        var context = new AnnotationConfigApplicationContext();
        singletonsToRegister.forEach(singleton -> context.getBeanFactory().registerSingleton(singleton.name(), singleton.object()));
        context.register(configurations.toArray(Class[]::new));
        context.refresh();
        return new SpringContext(context);
    }



}

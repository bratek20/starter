package pl.bratek20.commons.spring.web;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

public class SpringWebContextCreator {
    private final List<Class<?>> configurations;
    private ConfigurableApplicationContext context;

    public SpringWebContextCreator(Class<?>... configurations) {
        this.configurations = List.of(configurations);
    }

    private static int lastPort = 8080;
    public int port;
    public SpringWebContextCreator build() {
        var app = new SpringApplication(configurations.toArray(Class[]::new));
        port=lastPort;
        context = app.run("--server.port=" + lastPort);
        lastPort++;
        return this;
    }

    public <T> T get(Class<T> beanToGet) {
        return context.getBean(beanToGet);
    }
}


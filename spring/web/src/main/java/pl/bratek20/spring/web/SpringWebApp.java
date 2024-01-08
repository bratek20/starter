package pl.bratek20.spring.web;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import pl.bratek20.spring.di.SpringContext;

import java.util.ArrayList;
import java.util.List;

public class SpringWebApp {
    private final List<Class<?>> configurations;

    SpringWebApp(List<Class<?>> configurations) {
        this.configurations = new ArrayList<>(configurations);
        this.configurations.add(WebAppConfig.class);
    }

    SpringContext run(int port) {
        var app = new SpringApplication(configurations.toArray(Class[]::new));
        return new SpringContext(app.run("--server.port=" + port));
    }
}


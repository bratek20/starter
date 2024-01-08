package pl.bratek20.spring.web;

import org.springframework.boot.SpringApplication;
import pl.bratek20.spring.context.SpringContext;

import java.util.ArrayList;
import java.util.List;

public class WebApp {
    private final List<Class<?>> configurations;

    WebApp(List<Class<?>> configurations) {
        this.configurations = new ArrayList<>(configurations);
        this.configurations.add(WebAppConfig.class);
    }

    SpringContext run(int port) {
        var app = new SpringApplication(configurations.toArray(Class[]::new));
        return new SpringContext(app.run("--server.port=" + port));
    }

    public static void main(String[] args) {
        var app = new WebApp(List.of());
        app.run(8080);
    }
}


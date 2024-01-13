package pl.bratek20.spring.web;

import org.springframework.boot.SpringApplication;
import pl.bratek20.spring.context.SpringContext;

import java.util.ArrayList;
import java.util.List;

public class WebApp {
    private final List<Class<?>> configurations;
    private final String[] args;

    WebApp(List<Class<?>> configurations, boolean autoConfiguration, String[] args) {
        this.configurations = new ArrayList<>(configurations);
        this.args = args;

        if (autoConfiguration) {
            this.configurations.add(WebAppAutoConfig.class);
        }
        else {
            this.configurations.add(WebAppMinimalConfig.class);
        }
    }

    SpringContext run(int port) {
        var app = new SpringApplication(configurations.toArray(Class[]::new));
        var args2 = new ArrayList<>(List.of(this.args));
        args2.add("--server.port=" + port);
        return new SpringContext(app.run(args2.toArray(String[]::new)));
    }

    public static void main(String[] args) {
        var app = new WebApp(List.of(), true, args);
        app.run(8080);
    }

    public static void run(Class<?> config, String[] args) {
        var app = new WebApp(List.of(config), true, args);
        app.run(8080);
    }
}


package pl.bratek20.spring.web;

import org.springframework.context.ConfigurableApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class SpringWebAppBuilder {
    private final List<Class<?>> configurations;
    private ConfigurableApplicationContext context;

    public SpringWebAppBuilder(Class<?>... configurations) {
        this.configurations = new ArrayList<>(List.of(configurations));
        this.configurations.add(WebAppConfig.class);
    }

    private static int lastPort = 8080;
    public int port;
    public SpringWebAppBuilder build() {
        var app = new SpringWebApp(configurations);
        port = lastPort;
        context = app.run(port);
        lastPort++;
        return this;
    }

    public <T> T get(Class<T> beanToGet) {
        return context.getBean(beanToGet);
    }
}


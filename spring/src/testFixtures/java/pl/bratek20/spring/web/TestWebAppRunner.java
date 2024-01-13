package pl.bratek20.spring.web;

import lombok.Getter;
import pl.bratek20.spring.context.SpringContext;

import java.util.ArrayList;
import java.util.List;

public class TestWebAppRunner {
    private static int lastPort = 8080;

    private final List<Class<?>> configurations;
    @Getter
    private final int port;

    public TestWebAppRunner(Class<?>... configurations) {
        this.configurations = new ArrayList<>(List.of(configurations));
        port = lastPort++;
    }

    public SpringContext run() {
        var app = new WebApp(configurations, false, new String[]{});
        return app.run(port);
    }
}


package pl.bratek20.spring.web;

import lombok.Getter;
import pl.bratek20.spring.context.SpringContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestWebAppRunner {
    private final List<Class<?>> configurations;
    @Getter
    private final int port;

    public TestWebAppRunner(Class<?>... configurations) {
        this.configurations = new ArrayList<>(List.of(configurations));
        port = generateRandomPort();
    }

    public SpringContext run() {
        var app = new WebApp(configurations, new String[]{});
        return app.run(port);
    }

    public static int generateRandomPort() {
        int minPort = 8081;
        int maxPort = 65535;

        Random random = new Random();
        return random.nextInt(maxPort - minPort + 1) + minPort;
    }
}


package pl.bratek20.spring.app;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import pl.bratek20.spring.context.SpringContext;

@RequiredArgsConstructor
public class App {
    private final Class<?> configuration;
    private final String[] args;

    SpringContext run() {
        var context = new SpringApplicationBuilder(configuration, AppConfig.class)
            .web(WebApplicationType.NONE)
            .run(args);

        return new SpringContext(context);
    }

    public static SpringContext run(Class<?> config, String[] args) {
        var app = new App(config, args);
        return app.run();
    }
}

package pl.bratek20.spring.app;

import com.github.bratek20.architecture.context.spring.SpringContext;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;

@RequiredArgsConstructor
public class SpringApp {

    private final Class<?> configuration;
    private final String[] args;

    SpringContext run() {
        var configs = new Class<?>[]{SpringAppConfig.class};
        if (configuration != null) {
            configs = new Class<?>[]{configuration, SpringAppConfig.class};
        }
        var context = new SpringApplicationBuilder(configs)
            .web(WebApplicationType.NONE)
            .run(args);

        return new SpringContext(context);
    }

    public static SpringContext run(Class<?> config, String[] args) {
        var app = new SpringApp(config, args);
        return app.run();
    }
}

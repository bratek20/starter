package pl.bratek20.commons.modules.impl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.bratek20.commons.modules.api.Startable;

import java.util.Collection;

@Configuration
public class ModulesConfig {
    @Bean
    public StartCaller startCaller(Collection<Startable> startables) {
        return new StartCaller(startables);
    }
}

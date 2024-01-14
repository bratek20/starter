package pl.bratek20.spring.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import pl.bratek20.spring.config.BaseConfig;

@Configuration
@Import({
    BaseConfig.class,
})
class WebAppConfig {
    @Bean
    public HealthController healthController() {
        return new HealthController();
    }
}

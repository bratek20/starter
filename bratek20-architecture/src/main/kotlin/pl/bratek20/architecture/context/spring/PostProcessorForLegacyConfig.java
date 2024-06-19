package pl.bratek20.architecture.context.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PostProcessorForLegacyConfig {
    @Bean
    public static PostProcessorForLegacy postProcessorForLegacy(SpringContextBuilderProvider builderProvider) {
        return new PostProcessorForLegacy(builderProvider);
    }
}

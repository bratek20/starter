package pl.bratek20.commons.identity.impl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.bratek20.commons.identity.api.IdentityApi;

@Configuration
public class IdentityConfig {
    @Bean
    public IdentityApi identityApi() {
        return new IdentityService();
    }
}

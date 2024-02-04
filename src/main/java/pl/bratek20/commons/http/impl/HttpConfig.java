package pl.bratek20.commons.http.impl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.bratek20.commons.http.api.HttpApi;

@Configuration
public class HttpConfig {
    @Bean
    public HttpApi httpApi() {
        return new HttpImpl();
    }
}

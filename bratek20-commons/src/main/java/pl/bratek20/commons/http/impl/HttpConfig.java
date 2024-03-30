package pl.bratek20.commons.http.impl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.bratek20.commons.http.api.HttpClientFactory;

@Configuration
public class HttpConfig {
    @Bean
    public HttpClientFactory httpClientFactory() {
        return new HttpClientFactoryImpl();
    }
}

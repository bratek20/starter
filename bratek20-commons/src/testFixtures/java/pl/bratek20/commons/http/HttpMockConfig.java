package pl.bratek20.commons.http;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.bratek20.commons.http.api.HttpApi;

@Configuration
public class HttpMockConfig {
    @Bean
    public HttpApi httpApi(HttpApiMock mock) {
        return mock;
    }

    @Bean
    public HttpApiMock httpApiMock() {
        return new HttpApiMock();
    }
}

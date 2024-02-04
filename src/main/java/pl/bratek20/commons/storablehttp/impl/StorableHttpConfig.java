package pl.bratek20.commons.storablehttp.impl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.bratek20.commons.http.api.HttpApi;
import pl.bratek20.commons.storablehttp.api.StorableHttpApi;

@Configuration
public class StorableHttpConfig {
    @Bean
    public StorableHttpApi storableHttpApi(HttpApi httpApi) {
        return new StorableHttpImpl(httpApi);
    }
}

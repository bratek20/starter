package pl.bratek20.commons.multirequest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MultiRequestConfig {
    @Bean
    public MultiRequestService multiRequestService() {
        return new MultiRequestService();
    }

    @Bean
    public MultiRequestController multiRequestController(MultiRequestService multiRequestService) {
        return new MultiRequestController(multiRequestService);
    }
}

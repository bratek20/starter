package pl.bratek20.commons.user.impl.infrastructure.inmemory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.bratek20.commons.user.impl.application.UserRepository;

@Configuration
public class InMemoryConfig {
    @Bean
    public UserRepository userRepository() {
        return new InMemUserRepository();
    }
}

package pl.bratek20.commons.user.impl.infrastructure.persistance;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.bratek20.commons.user.impl.application.UserRepository;

@Configuration
public class CrudRepositoryConfig {

    @Bean
    public UserRepository userRepository(CrudUserEntityRepository crudUserEntityRepository) {
        return new CrudUserRepository(crudUserEntityRepository);
    }
}

package pl.bratek20.commons.user.impl.infrastructure.persistance;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import pl.bratek20.commons.user.impl.application.UserRepository;

@Configuration
@EnableJpaRepositories
@EntityScan
public class CrudRepositoryConfig {

    @Bean
    UserRepository userRepository(CrudUserEntityRepository crudUserEntityRepository) {
        return new CrudUserRepository(crudUserEntityRepository);
    }
}

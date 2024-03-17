package pl.bratek20.commons.user.impl.infrastructure.persistance;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import pl.bratek20.commons.user.impl.application.UserRepository;
import pl.bratek20.spring.flyway.api.FlywayMigration;

@Configuration
@EnableJdbcRepositories
public class CrudRepositoryConfig {
    @Bean
    FlywayMigration migrateUser() {
        return new FlywayMigration("users");
    }

    @Bean
    UserRepository userRepository(CrudUserEntityRepository crudUserEntityRepository) {
        return new CrudUserRepository(crudUserEntityRepository);
    }
}

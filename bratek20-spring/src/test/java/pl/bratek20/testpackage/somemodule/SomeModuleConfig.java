package pl.bratek20.testpackage.somemodule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import pl.bratek20.spring.flyway.api.FlywayMigration;

@Configuration
@EnableJdbcRepositories
public class SomeModuleConfig {
    @Bean
    public FlywayMigration migrateSomeModule() {
        return new FlywayMigration("some_module");
    }
}

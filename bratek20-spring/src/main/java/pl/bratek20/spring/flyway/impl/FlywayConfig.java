package pl.bratek20.spring.flyway.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.bratek20.spring.flyway.api.FlywayMigration;

import java.util.Collection;

@Configuration
public class FlywayConfig {
    @Value("${spring.datasource.url}")
    private String dataSourceUrl;

    @Bean
    public FlywayMigrator runFlywayMigrator(Collection<FlywayMigration> migrations) {
        var migrator = new FlywayMigrator(dataSourceUrl, migrations);
        migrator.migrate();

        return migrator;
    }
}

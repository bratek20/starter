package pl.bratek20.spring.flyway.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.bratek20.spring.data.DataSourceUrl;
import pl.bratek20.spring.flyway.api.FlywayMigration;

import java.util.Collection;

@Configuration
@RequiredArgsConstructor
public class FlywayConfig {
    private final DataSourceUrl dataSourceUrl;

    @Bean
    public FlywayMigrator runFlywayMigrator(Collection<FlywayMigration> migrations) {
        var migrator = new FlywayMigrator(dataSourceUrl.value(), migrations);
        migrator.migrate();

        return migrator;
    }
}

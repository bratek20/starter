package pl.bratek20.spring.data;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import pl.bratek20.spring.config.BaseConfig;
import pl.bratek20.spring.flyway.impl.FlywayMigrator;

import javax.sql.DataSource;

@Configuration
@Import({
    BaseConfig.class
})
public class InMemoryDataConfig {
    private static final String DB_URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DATABASE_TO_LOWER=TRUE;MODE=MySQL";

    @Bean
    public DataSource dataSource() {
        var dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");

        //https://stackoverflow.com/questions/31676596/spring-boot-flyway-h2-in-memory-caused-by-org-h2-jdbc-jdbcsqlexception-ta
        //https://www.h2database.com/html/features.html#in_memory_databases
        dataSource.setUrl(DB_URL);

        dataSource.setUsername(FlywayMigrator.FLYWAY_USERNAME);
        dataSource.setPassword(FlywayMigrator.FLYWAY_PASSWORD);
        return dataSource;
    }

    @Bean
    public DataSourceUrl dataSourceUrl(DataSource dataSource) {
        return new DataSourceUrl(DB_URL);
    }
}

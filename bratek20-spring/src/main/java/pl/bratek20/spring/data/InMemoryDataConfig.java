package pl.bratek20.spring.data;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import pl.bratek20.spring.config.BaseConfig;

import javax.sql.DataSource;

@Configuration
@Import({
    BaseConfig.class
})
public class InMemoryDataConfig {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");

        //https://stackoverflow.com/questions/31676596/spring-boot-flyway-h2-in-memory-caused-by-org-h2-jdbc-jdbcsqlexception-ta
        //https://www.h2database.com/html/features.html#in_memory_databases
        //probably DB_CLOSE_DELAY=-1 can be removed when every table will be in non-public schema
        dataSource.setUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");

        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
    }
}

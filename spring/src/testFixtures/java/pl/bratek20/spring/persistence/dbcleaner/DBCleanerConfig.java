package pl.bratek20.spring.persistence.dbcleaner;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DBCleanerConfig {
    @Bean
    public DBCleaner dbCleaner(JdbcTemplate jdbcTemplate) {
        return new DBCleaner(jdbcTemplate);
    }
}

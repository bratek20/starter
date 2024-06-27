package pl.bratek20.spring.data;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import pl.bratek20.spring.config.BaseConfig;

@Configuration
@Import({
    BaseConfig.class,
    DataSourceAutoConfiguration.class,
})
public class DefaultDataConfig {
    @Bean
    public DataSourceUrl dataSourceUrl(HikariDataSource dataSource) {
        return new DataSourceUrl(dataSource.getJdbcUrl());
    }
}

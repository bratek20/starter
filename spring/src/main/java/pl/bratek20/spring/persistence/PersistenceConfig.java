package pl.bratek20.spring.persistence;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import pl.bratek20.spring.config.BaseConfig;

@Configuration
@Import({
    BaseConfig.class,
    DataSourceAutoConfiguration.class,
})
@EnableJpaRepositories
@EntityScan
public class PersistenceConfig {

}

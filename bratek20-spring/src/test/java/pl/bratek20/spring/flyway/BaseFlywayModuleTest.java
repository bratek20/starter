package pl.bratek20.spring.flyway;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.bratek20.spring.context.SpringContextBuilder;
import pl.bratek20.spring.flyway.api.FlywayMigration;
import pl.bratek20.spring.flyway.impl.FlywayConfig;

import static org.assertj.core.api.Assertions.assertThat;

abstract class BaseFlywayModuleTest {
    protected abstract Class<?> getDataConfig();

    @Configuration
    static class Module1Config {
        @Bean
        public FlywayMigration migrateModule1() {
            return new FlywayMigration("module1");
        }
    }

    @Configuration
    static class Module2Config {
        @Bean
        public FlywayMigration migrateModule2() {
            return new FlywayMigration("module2");
        }
    }

    @Test
    void shouldMigrateTwoModules() {
        var context = new SpringContextBuilder()
            .withConfigs(
                getDataConfig(),
                FlywayConfig.class,
                Module1Config.class,
                Module2Config.class
            )
            .build();
        var jdbcTemplate = context.get(JdbcTemplate.class);

        assertModuleMigration(jdbcTemplate, "module1");
        assertModuleMigration(jdbcTemplate, "module2");
    }

    void assertModuleMigration(JdbcTemplate jdbcTemplate, String moduleName) {
        String insertSql = String.format("insert into %s.some_table values (1)", moduleName);
        jdbcTemplate.update(insertSql);

        String selectSql = String.format("select * from %s.some_table", moduleName);
        var l = jdbcTemplate.queryForList(selectSql);

        assertThat(l).hasSize(1);
        assertThat(l.get(0)).containsEntry("id", 1L);
    }
}
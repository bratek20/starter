package pl.bratek20.commons.user.impl;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import pl.bratek20.commons.user.api.UserApi;
import pl.bratek20.commons.user.api.UserPersistedApiTest;
import pl.bratek20.commons.user.impl.infrastructure.UserConfig;
import pl.bratek20.commons.user.impl.infrastructure.persistance.CrudRepositoryConfig;
import pl.bratek20.spring.context.SpringContextBuilder;
import pl.bratek20.spring.data.DefaultDataConfig;
import pl.bratek20.spring.data.MySQLExtension;
import pl.bratek20.spring.data.dbcleaner.DBCleaner;
import pl.bratek20.spring.flyway.impl.FlywayConfig;

@ExtendWith(MySQLExtension.class)
public class UserPersistedImplTest extends UserPersistedApiTest {
    private DBCleaner dbCleaner;

    @Override
    protected UserApi createApi() {
        var context = new SpringContextBuilder(
                UserConfig.class,
                CrudRepositoryConfig.class,
                FlywayConfig.class,
                DefaultDataConfig.class,
                DBCleaner.class
            )
            .build();

        dbCleaner = context.get(DBCleaner.class);

        return context.get(UserApi.class);
    }

    @Override
    protected void clean() {
        dbCleaner.deleteAllTables("users");
    }
}

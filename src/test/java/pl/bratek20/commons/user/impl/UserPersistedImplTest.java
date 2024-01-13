package pl.bratek20.commons.user.impl;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import pl.bratek20.commons.user.api.UserApi;
import pl.bratek20.commons.user.api.UserPersistedApiTest;
import pl.bratek20.commons.user.impl.infrastructure.UserConfig;
import pl.bratek20.commons.user.impl.infrastructure.persistance.CrudRepositoryConfig;
import pl.bratek20.spring.context.SpringContextBuilder;
import pl.bratek20.spring.persistence.MySQLExtension;
import pl.bratek20.spring.persistence.dbcleaner.DBCleaner;

@ExtendWith(MySQLExtension.class)
public class UserPersistedImplTest extends UserPersistedApiTest {

    @Configuration
    @EnableAutoConfiguration
    public static class MyPersistenceConfig {

    }

    private DBCleaner dbCleaner;

    @Override
    protected UserApi createApi() {
        var context = new SpringContextBuilder(
                UserConfig.class,
                CrudRepositoryConfig.class,
                MyPersistenceConfig.class,
                DBCleaner.class
            )
            .build();

        dbCleaner = context.get(DBCleaner.class);
        
        return context.get(UserApi.class);
    }

    @Override
    protected void clean() {
        dbCleaner.deleteAllTables();
    }
}

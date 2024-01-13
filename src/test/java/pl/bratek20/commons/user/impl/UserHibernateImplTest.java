package pl.bratek20.commons.user.impl;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import pl.bratek20.commons.user.api.UserApi;
import pl.bratek20.commons.user.api.UserPersistedApiTest;
import pl.bratek20.commons.user.impl.infrastructure.UserConfig;
import pl.bratek20.commons.user.impl.infrastructure.persistance.CrudRepositoryConfig;
import pl.bratek20.spring.context.SpringContextBuilder;
import pl.bratek20.spring.persistence.PersistenceInMemoryConfig;
import pl.bratek20.spring.persistence.dbcleaner.DBCleaner;

import javax.sql.DataSource;

public class UserHibernateImplTest extends UserPersistedApiTest {

    @Configuration
    @Import({
        DataSourceAutoConfiguration.class,
        JdbcTemplateAutoConfiguration.class,
        JpaRepositoriesAutoConfiguration.class,
    })
    //@EnableAutoConfiguration
    public static class MyPersistenceConfig {

    }

    @Configuration
    @EnableAutoConfiguration(exclude = {
        //DataSourceAutoConfiguration.class,
    })
    @Import({
        PersistenceInMemoryConfig.class,
    })
    public static class MyPersistenceAutoConfig {

    }

    private DBCleaner dbCleaner;

    @Override
    protected UserApi createApi() {
        var context = new SpringContextBuilder(
                UserConfig.class,
                CrudRepositoryConfig.class,
                MyPersistenceAutoConfig.class,
                //MyPersistenceConfig.class,
                DBCleaner.class
            )
            .build();

        dbCleaner = context.get(DBCleaner.class);
        dbCleaner.deleteAllTables();
        return context.get(UserApi.class);
    }

    @Override
    protected void clean() {
        dbCleaner.deleteAllTables();
    }
}

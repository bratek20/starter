package pl.bratek20.commons.user.impl;

import pl.bratek20.commons.user.api.UserApi;
import pl.bratek20.commons.user.api.UserPersistedApiTest;
import pl.bratek20.commons.user.impl.infrastructure.UserConfig;
import pl.bratek20.commons.user.impl.infrastructure.persistance.CrudRepositoryConfig;
import pl.bratek20.spring.context.SpringContextBuilder;
import pl.bratek20.spring.data.InMemoryDataConfig;
import pl.bratek20.spring.data.dbcleaner.DBCleaner;

public class UserHibernateImplTest extends UserPersistedApiTest {
    private DBCleaner dbCleaner;

    @Override
    protected UserApi createApi() {
        var context = new SpringContextBuilder(
                UserConfig.class,
                CrudRepositoryConfig.class,
                InMemoryDataConfig.class,
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

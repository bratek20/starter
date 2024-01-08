package pl.bratek20.commons.user.impl;

import org.junit.jupiter.api.extension.ExtendWith;
import pl.bratek20.commons.user.api.UserApi;
import pl.bratek20.commons.user.api.UserPersistedApiTest;
import pl.bratek20.commons.user.impl.infrastructure.UserConfig;
import pl.bratek20.commons.user.impl.infrastructure.persistance.CrudUserRepository;
import pl.bratek20.spring.context.SpringContextBuilder;
import pl.bratek20.spring.persistence.MySQLExtension;
import pl.bratek20.spring.persistence.PersistenceConfig;
import pl.bratek20.spring.persistence.dbcleaner.DBCleaner;

@ExtendWith(MySQLExtension.class)
public class UserPersistedImplTest extends UserPersistedApiTest {

    @Override
    protected UserApi createApi() {
        return new SpringContextBuilder(
                UserConfig.class,
                CrudUserRepository.class,
                PersistenceConfig.class,
                DBCleaner.class
            )
            .build()
            .get(UserApi.class);
    }
}

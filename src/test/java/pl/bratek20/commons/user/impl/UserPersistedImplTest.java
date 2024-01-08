package pl.bratek20.commons.user.impl;

import pl.bratek20.commons.user.api.UserApi;
import pl.bratek20.commons.user.api.UserPersistedApiTest;
import pl.bratek20.commons.user.impl.infra.UserConfig;
import pl.bratek20.spring.context.SpringContextBuilder;

public class UserPersistedImplTest extends UserPersistedApiTest {

    @Override
    protected UserApi createApi() {
        return new SpringContextBuilder(UserConfig.class)
            .build()
            .get(UserApi.class);
    }
}

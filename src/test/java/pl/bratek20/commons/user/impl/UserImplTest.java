package pl.bratek20.commons.user.impl;

import pl.bratek20.commons.spring.di.SpringContextBuilder;
import pl.bratek20.commons.user.api.UserApi;
import pl.bratek20.commons.user.api.UserApiTest;
import pl.bratek20.commons.user.impl.infra.UserConfig;

public class UserImplTest extends UserApiTest {

    @Override
    protected UserApi createApi() {
        return new SpringContextBuilder(UserConfig.class)
            .build()
            .get(UserApi.class);
    }
}

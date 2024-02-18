package pl.bratek20.commons.user.impl;

import pl.bratek20.commons.user.impl.infrastructure.inmemory.InMemUserRepository;
import pl.bratek20.spring.context.SpringContextBuilder;
import pl.bratek20.commons.user.api.UserApi;
import pl.bratek20.commons.user.api.UserApiTest;
import pl.bratek20.commons.user.impl.infrastructure.UserConfig;

public class UserImplTest extends UserApiTest {

    @Override
    protected UserApi createApi() {
        return new SpringContextBuilder(
                UserConfig.class,
                InMemUserRepository.class
            )
            .build()
            .get(UserApi.class);
    }
}

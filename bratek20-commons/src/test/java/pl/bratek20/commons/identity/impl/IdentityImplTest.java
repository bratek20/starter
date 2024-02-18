package pl.bratek20.commons.identity.impl;

import pl.bratek20.commons.identity.api.IdentityApi;
import pl.bratek20.commons.identity.api.IdentityApiTest;
import pl.bratek20.spring.context.SpringContextBuilder;

public class IdentityImplTest extends IdentityApiTest {

    @Override
    protected IdentityApi createApi() {
        return new SpringContextBuilder(IdentityConfig.class)
            .build()
            .get(IdentityApi.class);
    }
}

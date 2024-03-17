package pl.bratek20.commons.http.impl;

import pl.bratek20.commons.http.api.HttpApi;
import pl.bratek20.commons.http.api.HttpApiTest;
import pl.bratek20.spring.context.SpringContextBuilder;

class HttpImplTest extends HttpApiTest {

    @Override
    protected HttpApi createApi() {
        return new SpringContextBuilder(HttpConfig.class)
            .build()
            .get(HttpApi.class);
    }
}
package pl.bratek20.commons.http.impl;

import pl.bratek20.commons.http.api.HttpApiTest;
import pl.bratek20.commons.http.api.HttpClientFactory;
import pl.bratek20.spring.context.SpringContextBuilder;

class HttpImplTest extends HttpApiTest {

    @Override
    protected HttpClientFactory createInstance() {
        return new SpringContextBuilder(HttpConfig.class)
            .build()
            .get(HttpClientFactory.class);
    }
}
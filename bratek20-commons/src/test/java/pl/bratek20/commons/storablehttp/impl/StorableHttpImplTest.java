package pl.bratek20.commons.storablehttp.impl;

import pl.bratek20.commons.http.HttpApiMock;
import pl.bratek20.commons.storablehttp.api.StorableHttpApi;
import pl.bratek20.commons.storablehttp.api.StorableHttpApiTest;
import pl.bratek20.spring.context.SpringContextBuilder;

class StorableHttpImplTest extends StorableHttpApiTest {

    @Override
    protected StorableHttpApiTest.Context createContext() {
        var springContext = new SpringContextBuilder(
            HttpApiMock.class,
            StorableHttpConfig.class
        ).build();

        return new StorableHttpApiTest.Context(
            springContext.get(StorableHttpApi.class),
            springContext.get(HttpApiMock.class)
        );
    }
}
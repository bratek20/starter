package pl.bratek20.commons.http.impl;

import pl.bratek20.commons.http.api.HttpClient;
import pl.bratek20.commons.http.api.HttpClientFactory;

public class HttpClientFactoryImpl implements HttpClientFactory {

    @Override
    public HttpClient create(String baseUrl) {
        return new HttpClientImpl(baseUrl);
    }
}

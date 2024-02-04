package pl.bratek20.commons.http.impl;

import pl.bratek20.commons.http.api.HttpApi;
import pl.bratek20.commons.http.api.HttpRequest;
import pl.bratek20.commons.http.api.HttpResponse;

public class HttpImpl implements HttpApi {

    @Override
    public HttpResponse send(HttpRequest request) {
        return new HttpResponse(200);
    }

    @Override
    public void store(HttpRequest request) {

    }

    @Override
    public void sendStored() {

    }
}

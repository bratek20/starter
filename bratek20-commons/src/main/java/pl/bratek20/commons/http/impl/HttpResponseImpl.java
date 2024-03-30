package pl.bratek20.commons.http.impl;

import lombok.RequiredArgsConstructor;
import pl.bratek20.commons.http.api.HttpResponse;

@RequiredArgsConstructor
public class HttpResponseImpl implements HttpResponse {
    private final int statusCode;
    private final String body;

    @Override
    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public String getBody() {
        return body;
    }
}

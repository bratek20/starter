package pl.bratek20.commons.http.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import pl.bratek20.commons.http.api.HttpResponse;

@RequiredArgsConstructor
public class HttpResponseImpl implements HttpResponse {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final int statusCode;
    private final String body;

    @Override
    public int getStatusCode() {
        return statusCode;
    }

    @Override
    @SneakyThrows
    public <T> T getBody(Class<T> clazz) {
        return OBJECT_MAPPER.readValue(body, clazz);
    }
}

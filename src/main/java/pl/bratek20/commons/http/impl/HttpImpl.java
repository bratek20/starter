package pl.bratek20.commons.http.impl;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import pl.bratek20.commons.http.api.HttpApi;
import pl.bratek20.commons.http.api.HttpRequest;
import pl.bratek20.commons.http.api.HttpRequestType;
import pl.bratek20.commons.http.api.HttpResponse;

public class HttpImpl implements HttpApi {

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public HttpResponse send(HttpRequest request) {
        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                request.url(),
                map(request.type()),
                null,
                String.class
            );

            return new HttpResponse(responseEntity.getStatusCode().value(), responseEntity.getBody());
        } catch (RestClientException e) {
            throw e;
        }
    }

    private HttpMethod map(HttpRequestType type) {
        switch (type) {
            case GET:
                return HttpMethod.GET;
            default:
                throw new IllegalArgumentException("Unsupported request type: " + type);
        }
    }

    @Override
    public void store(HttpRequest request) {

    }

    @Override
    public void sendStored() {

    }
}

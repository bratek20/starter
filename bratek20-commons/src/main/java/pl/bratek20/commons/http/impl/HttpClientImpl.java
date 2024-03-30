package pl.bratek20.commons.http.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import pl.bratek20.commons.http.api.*;

@RequiredArgsConstructor
public class HttpClientImpl implements HttpClient {
    private final String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public HttpResponse get(String path) {
        String url = baseUrl + path;

        ResponseEntity<String> responseEntity = restTemplate.exchange(
            url,
            HttpMethod.GET,
            null,
            String.class
        );

        return new HttpResponseImpl(responseEntity.getStatusCode().value(), responseEntity.getBody());
    }

    @Override
    public <T> HttpResponse post(String path, T body) {
        return null;
    }
}

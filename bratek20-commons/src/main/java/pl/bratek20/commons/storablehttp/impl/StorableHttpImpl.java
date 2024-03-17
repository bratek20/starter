package pl.bratek20.commons.storablehttp.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import pl.bratek20.commons.http.api.HttpApi;
import pl.bratek20.commons.http.api.HttpRequest;
import pl.bratek20.commons.http.api.HttpRequestType;
import pl.bratek20.commons.http.api.HttpResponse;
import pl.bratek20.commons.storablehttp.api.StorableHttpApi;

@RequiredArgsConstructor
public class StorableHttpImpl implements StorableHttpApi {
    private final HttpApi httpApi;

    @Override
    public HttpResponse send(HttpRequest request) {
         return httpApi.send(request);
    }

    @Override
    public void store(HttpRequest request) {

    }

    @Override
    public void sendStored() {

    }
}

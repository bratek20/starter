package com.github.bratek20.infrastructure.httpclient.impl

import com.github.bratek20.infrastructure.httpclient.api.*

class HttpClientFactoryLogic: HttpClientFactory {
    override fun create(baseUrl: String): HttpClient {
        TODO("Not yet implemented")
    }
}

//@RequiredArgsConstructor
//public class HttpClientImpl implements HttpClient {
//    private final String baseUrl;
//    private final RestTemplate restTemplate = new RestTemplate();
//
//    @Override
//    public HttpResponse get(String path) {
//        ResponseEntity<String> responseEntity = restTemplate.exchange(
//                getFullUrl(path),
//        HttpMethod.GET,
//        null,
//        String.class
//        );
//
//        return new HttpResponseImpl(responseEntity.getStatusCode().value(), responseEntity.getBody());
//    }
//
//    @Override
//    public <T> HttpResponse post(String path, T body) {
//        ResponseEntity<String> responseEntity = restTemplate.exchange(
//                getFullUrl(path),
//        HttpMethod.POST,
//        new HttpEntity<>(body),
//        String.class
//        );
//
//        return new HttpResponseImpl(responseEntity.getStatusCode().value(), responseEntity.getBody());
//    }
//
//    private String getFullUrl(String path) {
//        return baseUrl + path;
//    }
//}
//
//@RequiredArgsConstructor
//public class HttpResponseImpl implements HttpResponse {
//    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
//
//    private final int statusCode;
//    private final String body;
//
//    @Override
//    public int getStatusCode() {
//        return statusCode;
//    }
//
//    @Override
//    @SneakyThrows
//    public <T> T getBody(Class<T> clazz) {
//        return OBJECT_MAPPER.readValue(body, clazz);
//    }
//}
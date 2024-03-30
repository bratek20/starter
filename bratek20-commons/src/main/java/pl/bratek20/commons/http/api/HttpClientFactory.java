package pl.bratek20.commons.http.api;

public interface HttpClientFactory {
    HttpClient create(String baseUrl);
}

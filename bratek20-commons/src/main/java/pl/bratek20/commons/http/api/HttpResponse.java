package pl.bratek20.commons.http.api;

public interface HttpResponse {
    int getStatusCode();
    <T> T getBody(Class<T> clazz);
}

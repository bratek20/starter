package pl.bratek20.commons.http.api;

public interface HttpClient {
    HttpResponse get(String path);
    <T> HttpResponse post(String path, T body);
}

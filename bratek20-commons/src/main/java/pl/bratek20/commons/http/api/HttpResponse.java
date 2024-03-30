package pl.bratek20.commons.http.api;

public interface HttpResponse {
    int getStatusCode();
    String getBody();
}

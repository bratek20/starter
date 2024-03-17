package pl.bratek20.commons.http.api;

public record HttpRequest(
    HttpRequestType type,
    String url
) {}

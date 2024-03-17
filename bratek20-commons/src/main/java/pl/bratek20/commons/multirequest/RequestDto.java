package pl.bratek20.commons.multirequest;

import pl.bratek20.commons.http.api.HttpRequestType;

public record RequestDto(HttpRequestType type, String url, String body) {

}

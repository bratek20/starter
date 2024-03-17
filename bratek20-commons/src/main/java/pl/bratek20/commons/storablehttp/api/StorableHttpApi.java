package pl.bratek20.commons.storablehttp.api;

import pl.bratek20.commons.http.api.HttpApi;
import pl.bratek20.commons.http.api.HttpRequest;

public interface StorableHttpApi extends HttpApi {
    void store(HttpRequest request);
    void sendStored();
}

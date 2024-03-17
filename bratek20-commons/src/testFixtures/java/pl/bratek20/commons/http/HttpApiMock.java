package pl.bratek20.commons.http;

import pl.bratek20.commons.http.api.HttpApi;
import pl.bratek20.commons.http.api.HttpRequest;
import pl.bratek20.commons.http.api.HttpResponse;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpApiMock implements HttpApi {

    private final List<HttpRequest> requests = new ArrayList<>();

    @Override
    public HttpResponse send(HttpRequest request) {
        requests.add(request);
        return null;
    }

    public void assertOneSend(HttpRequest request) {
        assertThat(requests).containsExactly(request);
    }
}

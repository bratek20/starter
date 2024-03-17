package pl.bratek20.commons.storablehttp.api;

import org.junit.jupiter.api.Test;
import pl.bratek20.commons.http.HttpApiMock;
import pl.bratek20.commons.http.api.HttpRequest;
import pl.bratek20.commons.http.api.HttpRequestType;
import pl.bratek20.commons.modules.BaseContextTest;

public abstract class StorableHttpApiTest extends BaseContextTest<StorableHttpApiTest.Context> {
    public record Context(StorableHttpApi api, HttpApiMock httpApiMock) {
    }

    private StorableHttpApi api;
    private HttpApiMock httpApiMock;

    @Override
    protected void applyContext(Context context) {
        api = context.api;
        httpApiMock = context.httpApiMock;
    }

    @Test
    void shouldDelegateSend() {
        var request = new HttpRequest(HttpRequestType.GET, "http://localhost:8080");
        api.send(request);

        httpApiMock.assertOneSend(request);
    }
}
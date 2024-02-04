package pl.bratek20.commons.http.api;

import org.junit.jupiter.api.Test;
import pl.bratek20.commons.modules.BaseApiTest;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class HttpApiTest extends BaseApiTest<HttpApi> {

    @Override
    protected void setup() {

    }

    @Test
    void shouldSendGetRequest() {
        HttpRequest request = new HttpRequest(
                HttpRequestType.GET,
                "http://localhost:8080/get"
        );

        var response = api.send(request);

        assertThat(response.code()).isEqualTo(200);
    }
}
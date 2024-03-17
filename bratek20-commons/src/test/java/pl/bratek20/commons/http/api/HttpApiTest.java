package pl.bratek20.commons.http.api;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import pl.bratek20.architecture.tests.ApiTest;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class HttpApiTest extends ApiTest<HttpApi> {

    private WireMockServer server;

    @Override
    protected void setup() {
        super.setup();
        server = new WireMockServer(8080);
        server.start();
    }

    @Override
    protected void clean() {
        super.clean();
        if (server != null) {
            server.stop();
        }
    }

    @Test
    void shouldSendGetRequest() {
        server.stubFor(
            WireMock.get(WireMock.urlEqualTo("/get"))
            .willReturn(WireMock.aResponse()
                .withHeader("Content-Type", "application/json")
                .withStatus(200)
                .withBody("{\"message\": \"Hello World\"}")));

        HttpRequest request = new HttpRequest(
                HttpRequestType.GET,
                "http://localhost:8080/get"
        );

        var response = api.send(request);

        assertThat(response.code()).isEqualTo(200);
        assertThat(response.body()).isEqualTo("{\"message\": \"Hello World\"}");
    }
}
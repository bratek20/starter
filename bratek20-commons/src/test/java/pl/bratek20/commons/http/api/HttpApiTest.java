package pl.bratek20.commons.http.api;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import pl.bratek20.architecture.tests.ApiTest;
import pl.bratek20.tests.InterfaceTest;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class HttpApiTest extends InterfaceTest<HttpClientFactory> {

    private WireMockServer server;
    private HttpClient client;

    @Override
    protected void setup() {
        super.setup();
        server = new WireMockServer(8080);
        server.start();

        client = instance.create("http://localhost:8080");
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

        var response = client.get("/get");

        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("{\"message\": \"Hello World\"}");
    }
}
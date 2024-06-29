package pl.bratek20.commons.http.api;

import com.github.bratek20.infrastructure.httpclient.api.HttpClient;
import com.github.bratek20.infrastructure.httpclient.impl.HttpClientFactoryLogic;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpApiTest {

    private WireMockServer server;
    private HttpClient client;

    @BeforeEach
    void setup() {
        server = new WireMockServer(8080);
        server.start();

        client = new HttpClientFactoryLogic().create("http://localhost:8080");
    }

    @AfterEach
    void clean() {
        if (server != null) {
            server.stop();
        }
    }

    record ExampleBody(String message) {}

    @Test
    void shouldSupportGet() {
        server.stubFor(
            WireMock.get(WireMock.urlEqualTo("/get"))
            .willReturn(WireMock.aResponse()
                .withHeader("Content-Type", "application/json")
                .withStatus(200)
                .withBody("{\"message\": \"Hello World\"}")));

        var response = client.get("/get");

        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getBody(ExampleBody.class))
            .isEqualTo(new ExampleBody("Hello World"));
    }

    @Test
    void shouldSupportPost() {
        server.stubFor(
            WireMock.post(WireMock.urlEqualTo("/post"))
                .withRequestBody(WireMock.equalToJson("{\"message\": \"Request\"}"))
                .willReturn(WireMock.aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withStatus(200)
                    .withBody("{\"message\": \"Response\"}")));

        var response = client.post("/post", new ExampleBody("Request"));

        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getBody(ExampleBody.class))
            .isEqualTo(new ExampleBody("Response"));
    }
}
package pl.bratek20.spring.web;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Configuration;

class WebAppTest {
    @Configuration
    static class EmptyConfig {}

    @Test
    void shouldRunWebApp() {
        WebApp.run(EmptyConfig.class, new String[]{});

        RestAssured.port = 8080;
        RestAssured
            .given()
            .when()
            .get("/health")
            .then()
            .statusCode(200);
    }
}
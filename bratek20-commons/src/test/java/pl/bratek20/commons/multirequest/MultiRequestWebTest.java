package pl.bratek20.commons.multirequest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.bratek20.spring.web.TestWebAppRunner;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MultiRequestWebTest {
    @RestController
    static class SomeController {
        @GetMapping("/get")
        public String get() {
            return "get";
        }

        @GetMapping("/post")
        public String post() {
            return "post";
        }
    }

    void setup() {
        var runner = new TestWebAppRunner(
            SomeController.class,
            MultiRequestConfig.class
        );

        RestAssured.port = runner.getPort();

        runner.run();
    }

    @Test
    void shouldDelegateRequestsInOrderAndReturnResultFromLastOne() {
        // given
        setup();

        // when
        var response = given()
            .contentType(ContentType.JSON)
            .body("")
            .when()
            .post("/multi-request")
            .then()
            .statusCode(200)
            .extract()
            .asString();

        // then
        assertThat(response).isEqualTo("get");
    }
}
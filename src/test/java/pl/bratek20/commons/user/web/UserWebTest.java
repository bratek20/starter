package pl.bratek20.commons.user.web;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import pl.bratek20.commons.identity.api.IdentityId;
import pl.bratek20.commons.user.api.User;
import pl.bratek20.commons.user.api.UserApi;
import pl.bratek20.commons.user.api.UserApiTest;
import pl.bratek20.commons.user.api.exceptions.UserAlreadyExistsException;
import pl.bratek20.commons.user.api.exceptions.UserNotExistsException;
import pl.bratek20.commons.user.api.exceptions.WrongUserPasswordException;
import pl.bratek20.spring.web.TestWebAppRunner;

import static io.restassured.RestAssured.given;

class UserWebTest extends UserApiTest {

    @Override
    protected UserApi createApi() {
        var runner = new TestWebAppRunner(
            UserWebServerConfig.class
        );
        runner.run();
        RestAssured.port = runner.getPort();
        return new WebClient();
    }

    static class WebClient implements UserApi {

        @Override
        public IdentityId create(User user) throws UserAlreadyExistsException {
            String url = "/user/create";

            String body = """
                {
                    "name": "%s",
                    "password": "%s"
                }
                """.formatted(user.name(), user.password());

            var response = given()
                .contentType(ContentType.JSON)
                .body(body)
            .when()
                .post(url);

            if (response.statusCode() == 404) {
                throw new UserAlreadyExistsException(user.name());
            }

            var id = response.then()
                .statusCode(200)
                .extract().body().as(Long.class);
            return new IdentityId(id);
        }

        @Override
        public IdentityId getIdentityId(User user) throws UserNotExistsException, WrongUserPasswordException {
            String url = "/user/getIdentityId";

            String body = """
                {
                    "name": "%s",
                    "password": "%s"
                }
                """.formatted(user.name(), user.password());

            var response = given()
                .contentType(ContentType.JSON)
                .body(body)
            .when()
                .post(url);

            if (response.statusCode() == 404) {
                if (response.getBody().asString().equals("Wrong user password")) {
                    throw new WrongUserPasswordException();
                }
                throw new UserNotExistsException(user.name());
            }

            var id = response.then()
                .statusCode(200)
                .extract().body().as(Long.class);
            return new IdentityId(id);
        }
    }
}

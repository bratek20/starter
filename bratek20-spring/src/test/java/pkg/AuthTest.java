package pkg;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
    classes = WebApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class AuthTest {

    @LocalServerPort
    private int port;

    private final RestTemplate restTemplate = new RestTemplate();

    private String getBaseUrl() {
        return "http://localhost:" + port;
    }

    @Test
    void testFullAuthAndSessionFlow() {
        String authId = "test-auth-id";

        // Step 1: Login request
        HttpHeaders loginHeaders = new HttpHeaders();
        loginHeaders.add("AuthId", authId);
        HttpEntity<String> loginEntity = new HttpEntity<>(null, loginHeaders);

        ResponseEntity<String> loginResponse = restTemplate.exchange(
            getBaseUrl() + "/login", HttpMethod.GET, loginEntity, String.class);

        assertThat(loginResponse.getBody()).contains("Logged in as 1");

        // Extract session cookies after login
        List<String> sessionCookies = loginResponse.getHeaders().get(HttpHeaders.SET_COOKIE);
        assertThat(sessionCookies).isNotEmpty();

        // Step 2: Test session-scoped bean with the logged-in session
        HttpHeaders sessionHeaders = new HttpHeaders();
        setSessionCookies(sessionHeaders, sessionCookies);

        ResponseEntity<String> userScopedBeanResponse1 = restTemplate.exchange(
            getBaseUrl() + "/user-scope", HttpMethod.GET, new HttpEntity<>(sessionHeaders), String.class);

        ResponseEntity<String> userScopedBeanResponse2 = restTemplate.exchange(
            getBaseUrl() + "/user-scope", HttpMethod.GET, new HttpEntity<>(sessionHeaders), String.class);

        // Validate that the same session-scoped bean is returned within the same session
        assertThat(userScopedBeanResponse1.getBody()).isEqualTo(userScopedBeanResponse2.getBody());
        assertThat(userScopedBeanResponse1.getBody()).isEqualTo("1");

        // Step 3: Test secure endpoint with the logged-in session
        ResponseEntity<String> secureResponse = restTemplate.exchange(
            getBaseUrl() + "/secure-endpoint", HttpMethod.GET, new HttpEntity<>(sessionHeaders), String.class);

        assertThat(secureResponse.getBody()).contains("Welcome back, 1");

        // Step 4: Simulate a second user session

        //login with other auth id
        String authId2 = "test-auth-id2";
        HttpHeaders loginHeaders2 = new HttpHeaders();
        loginHeaders2.add("AuthId", authId2);
        HttpEntity<String> loginEntity2 = new HttpEntity<>(null, loginHeaders2);

        ResponseEntity<String> loginResponse2 = restTemplate.exchange(
            getBaseUrl() + "/login", HttpMethod.GET, loginEntity2, String.class);

        assertThat(loginResponse2.getBody()).contains("Logged in as 2");

        // Extract session cookies after login
        List<String> sessionCookies2 = loginResponse2.getHeaders().get(HttpHeaders.SET_COOKIE);
        assertThat(sessionCookies2).isNotEmpty();


        HttpHeaders newSessionHeaders = new HttpHeaders();
        setSessionCookies(newSessionHeaders, sessionCookies2);

        ResponseEntity<String> userScopedBeanResponseNewSession = restTemplate.exchange(
            getBaseUrl() + "/user-scope", HttpMethod.GET, new HttpEntity<>(newSessionHeaders), String.class);

        // Validate that the session-scoped bean is different for a new session
        assertThat(userScopedBeanResponse1.getBody()).isNotEqualTo(userScopedBeanResponseNewSession.getBody());

        // Step 5: Test application-scoped bean shared across sessions
        ResponseEntity<String> applicationScopedBeanResponse1 = restTemplate.exchange(
            getBaseUrl() + "/application-scope", HttpMethod.GET, new HttpEntity<>(sessionHeaders), String.class);

        ResponseEntity<String> applicationScopedBeanResponse2 = restTemplate.exchange(
            getBaseUrl() + "/application-scope", HttpMethod.GET, new HttpEntity<>(newSessionHeaders), String.class);

        // Validate that the application-scoped bean is shared across sessions
        assertThat(applicationScopedBeanResponse1.getBody()).isEqualTo(applicationScopedBeanResponse2.getBody());
    }

    private void setSessionCookies(HttpHeaders headers, List<String> cookies) {
        if (cookies != null) {
            headers.add(HttpHeaders.COOKIE, String.join("; ", cookies));
        }
    }
}

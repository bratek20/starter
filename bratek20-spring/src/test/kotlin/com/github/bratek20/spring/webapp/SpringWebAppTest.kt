package com.github.bratek20.spring.webapp

import com.github.bratek20.architecture.context.api.ContextBuilder
import com.github.bratek20.architecture.exceptions.ApiException
import com.github.bratek20.infrastructure.httpserver.api.WebServerModule
import com.github.bratek20.logs.context.SystemLogsImpl
import io.restassured.RestAssured
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import pkg.*


class MyException(message: String): ApiException(message)

class SpringWebAppTest {
    @Test
    fun `should run on different ports`() {
        SpringWebApp.run() // default port should be 8080
        SpringWebApp.run(port = 8081)

        RestAssured.port = 8080
        RestAssured
            .given()
            .`when`()["/health"]
            .then()
            .statusCode(200)

        RestAssured.port = 8081
        RestAssured
            .given()
            .`when`()["/health"]
            .then()
            .statusCode(200)
    }

    @RestController
    class ThrowingController {
        @PostMapping("/throw")
        fun `throw`() {
            throw MyException("test")
        }
    }

    class ThrowingModule: WebServerModule {
        override fun getControllers(): List<Class<*>> {
            return listOf(
                ThrowingController::class.java
            )
        }

        override fun apply(builder: ContextBuilder) {
            // no-op
        }
    }

    @Test
    fun shouldPassApiExceptions() {
        RestAssured.port = SpringWebApp.run(
            modules = listOf(
                SystemLogsImpl(),
                ThrowingModule()
            ),
            useRandomPort = true,
        ).port

        RestAssured
            .given()
            .`when`()
            .post("/throw")
            .then()
            .statusCode(200)
            .body(equalTo("""
                {
                    "passedException": {
                        "type": "MyException",
                        "package": "com.github.bratek20.spring.webapp",
                        "message": "test"
                    }
                }
            """.trimIndent()))
    }

    @Nested
    inner class UserScopeSupport {
        private val restTemplate = RestTemplate()

        private var port: Int? = null
        private fun getBaseUrl(): String {
            return "http://localhost:$port"
        }

        @Test
        fun testFullAuthAndSessionFlow() {
            port = SpringWebApp.run(
                modules = listOf(
                    SystemLogsImpl(),
                    AuthControllerImpl(),
                    ApplicationModule(),
                    ApplicationControllerModule(),
                ),
                configs = listOf(
                    UserConfig::class.java,
                ),
                useRandomPort = true,
            ).port

            val authId = "test-auth-id"

            // Step 1: Login request
            val loginHeaders = HttpHeaders()
            loginHeaders.add("AuthId", authId)
            val loginEntity = HttpEntity<String?>(null, loginHeaders)

            val loginResponse = restTemplate.exchange(
                getBaseUrl() + "/login", HttpMethod.GET, loginEntity, String::class.java
            )

            Assertions.assertThat(loginResponse.body).contains("Logged in as 1")

            // Extract session cookies after login
            val sessionCookies = loginResponse.headers[HttpHeaders.SET_COOKIE]!!
            assertThat(sessionCookies).hasSize(1)
            assertThat(sessionCookies[0]).contains("JSESSIONID=")

            // Step 2: Test session-scoped bean with the logged-in session
            val sessionHeaders = HttpHeaders()
            setSessionCookies(sessionHeaders, sessionCookies)

            val userScopedBeanResponse1 = restTemplate.exchange(
                getBaseUrl() + "/user-scope-id", HttpMethod.GET, HttpEntity<Any>(sessionHeaders),
                String::class.java
            )

            val userScopedBeanResponse2 = restTemplate.exchange(
                getBaseUrl() + "/user-scope-id", HttpMethod.GET, HttpEntity<Any>(sessionHeaders),
                String::class.java
            )

            // Validate that the same session-scoped bean is returned within the same session
            Assertions.assertThat(userScopedBeanResponse1.body).isEqualTo(userScopedBeanResponse2.body)
            //Assertions.assertThat(userScopedBeanResponse1.body).isEqualTo("1")

            // Step 4: Simulate a second user session

            //login with other auth id
            val authId2 = "test-auth-id2"
            val loginHeaders2 = HttpHeaders()
            loginHeaders2.add("AuthId", authId2)
            val loginEntity2 = HttpEntity<String?>(null, loginHeaders2)

            val loginResponse2 = restTemplate.exchange(
                getBaseUrl() + "/login", HttpMethod.GET, loginEntity2, String::class.java
            )

            Assertions.assertThat(loginResponse2.body).contains("Logged in as 2")

            // Extract session cookies after login
            val sessionCookies2 = loginResponse2.headers[HttpHeaders.SET_COOKIE]
            Assertions.assertThat(sessionCookies2).isNotEmpty()


            val newSessionHeaders = HttpHeaders()
            setSessionCookies(newSessionHeaders, sessionCookies2)

            val userScopedBeanResponseNewSession = restTemplate.exchange(
                getBaseUrl() + "/user-scope-id", HttpMethod.GET, HttpEntity<Any>(newSessionHeaders),
                String::class.java
            )

            // Validate that the session-scoped bean is different for a new session
            Assertions.assertThat(userScopedBeanResponse1.body).isNotEqualTo(userScopedBeanResponseNewSession.body)

            // Step 5: Test application-scoped bean shared across sessions
            val applicationScopedBeanResponse1 = restTemplate.exchange(
                getBaseUrl() + "/application-scope", HttpMethod.GET, HttpEntity<Any>(sessionHeaders),
                String::class.java
            )

            val applicationScopedBeanResponse2 = restTemplate.exchange(
                getBaseUrl() + "/application-scope", HttpMethod.GET, HttpEntity<Any>(newSessionHeaders),
                String::class.java
            )

            // Validate that the application-scoped bean is shared across sessions
            Assertions.assertThat(applicationScopedBeanResponse1.body).isEqualTo(applicationScopedBeanResponse2.body)
        }

        private fun setSessionCookies(headers: HttpHeaders, cookies: List<String>?) {
            if (cookies != null) {
                headers.add(HttpHeaders.COOKIE, java.lang.String.join("; ", cookies))
            }
        }
    }
}
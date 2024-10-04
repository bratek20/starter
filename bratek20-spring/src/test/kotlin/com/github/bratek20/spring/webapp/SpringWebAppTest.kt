package com.github.bratek20.spring.webapp

import com.github.bratek20.architecture.context.api.ContextBuilder
import com.github.bratek20.architecture.context.api.ContextModule
import com.github.bratek20.architecture.exceptions.ApiException
import com.github.bratek20.infrastructure.httpserver.api.WebServerModule
import com.github.bratek20.logs.context.SystemLogsImpl
import io.restassured.RestAssured
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.Test
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController


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
        RestAssured.port= SpringWebApp.run(
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
}
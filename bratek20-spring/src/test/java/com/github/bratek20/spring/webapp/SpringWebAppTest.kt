package com.github.bratek20.spring.webapp

import io.restassured.RestAssured
import org.junit.jupiter.api.Test

class SpringWebAppTest {
    @Test
    fun `should run on different ports`() {
        SpringWebApp.run()
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
}
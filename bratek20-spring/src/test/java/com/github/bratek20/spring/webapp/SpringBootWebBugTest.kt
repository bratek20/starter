package com.github.bratek20.spring.webapp

import io.restassured.RestAssured
import org.junit.jupiter.api.Test
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/health")
class TestHealthController {
    @GetMapping
    fun get(): String {
        return "OK"
    }
}

@Configuration
open class TestHealthControllerConfig {
    @Bean
    open fun testHealthController(): TestHealthController {
        return TestHealthController()
    }
}

@Configuration
@EnableAutoConfiguration
open class TestWebAppConfig



class SpringBootWebBugTest {
    @Test
    fun healthControllerAsSource() {
        SpringApplicationBuilder(TestWebAppConfig::class.java, TestHealthController::class.java)
            .run("--server.port=8077")

        assertHealth(8077)
    }

    @Test
    fun healthControllerAsParent() {
        val context = AnnotationConfigApplicationContext()
        context.register(TestHealthController::class.java)
        context.refresh()

        SpringApplicationBuilder(TestWebAppConfig::class.java)
            .parent(context)
            .run("--server.port=8078")

        assertHealth(8078)
    }

    @Test
    fun healthControllerAsParent_config() {
        val context = AnnotationConfigApplicationContext()
        context.register(TestHealthControllerConfig::class.java)
        context.refresh()

        SpringApplicationBuilder(TestWebAppConfig::class.java)
            .parent(context)
            .run("--server.port=8079")

        assertHealth(8079)
    }

    private fun assertHealth(port: Int) {
        RestAssured.port = port
        RestAssured
            .given()
            .`when`()["/health"]
            .then()
            .statusCode(200)
    }
}
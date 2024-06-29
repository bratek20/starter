package com.github.bratek20.spring.webapp

import io.restassured.RestAssured
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

class MyConfig

class EmptyConfig



class SpringWebAppTest {

    @Test
    fun register() {
        val context = AnnotationConfigApplicationContext()
        context.register(MyConfig::class.java)
        context.refresh()

        context.beanDefinitionNames.find { it == "myConfig" }.let {
            assertThat(it).isNotNull()
        }

        val emptyContext = SpringApplicationBuilder(EmptyConfig::class.java)
            .web(WebApplicationType.NONE)
            .parent(context)
            .run("--server.port=8078")

        emptyContext.beanDefinitionNames.find { it == "myConfig" }.let {
            assertThat(it).withFailMessage("emptyContext does not have myConfig").isNotNull()
        }

        val webAppContext = SpringApplicationBuilder(WebAppConfig::class.java)
            .parent(context)
            .run("--server.port=8078")

        webAppContext.beanDefinitionNames.find { it == "myConfig" }.let {
            assertThat(it).withFailMessage("webAppContext does not have myConfig").isNotNull()
        }
    }




    fun assertBeanRegistered(context: ConfigurableApplicationContext, beanName: String) {
        assertThat(context.containsBean(beanName)).isTrue()


    }

    @Test
    fun `should have health controller`() {
        val context = SpringWebApp.run(port = 8079)

        val healthController = context.get(HealthController::class.java)
        assertThat(healthController).isNotNull()

        assertBeanRegistered(context.value, "healthController")
    }



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
package com.github.bratek20.spring.webapp

import com.github.bratek20.architecture.context.api.ContextBuilder
import com.github.bratek20.architecture.context.api.ContextModule
import com.github.bratek20.architecture.context.spring.SpringContextBuilder
import io.restassured.RestAssured
import org.assertj.core.api.Assertions.assertThatCode
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.Test
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SomeController {
    @PostMapping("/ping")
    fun ping(): String {
        return "pong"
    }
}

class SomeModuleWeb: ContextModule {
    override fun apply(builder: ContextBuilder) {
        builder.setClass(SomeController::class.java)
    }
}

class ControllerIntegrationTest {
    @Test
    fun `should use controller added as class in arch context`() {
        val context = SpringWebApp.run(SpringContextBuilder()
            .withModules(
                SomeModuleWeb()
            ) as SpringContextBuilder
        )

        //check if controller in final context
        assertThatCode { context.get(SomeController::class.java) }.doesNotThrowAnyException()

        RestAssured.port = 8080
        RestAssured
            .given()
            .`when`()
            .post("/ping")
            .then()
            .statusCode(200)
            .body(equalTo("pong"))
    }
}
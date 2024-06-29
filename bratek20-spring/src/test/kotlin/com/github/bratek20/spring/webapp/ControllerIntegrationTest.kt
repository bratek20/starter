package com.github.bratek20.spring.webapp

import com.github.bratek20.architecture.context.api.ContextBuilder
import com.github.bratek20.architecture.context.api.ContextModule
import io.restassured.RestAssured
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.Test
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

interface SomeApi {
    fun ping(): String
}

class SomeApiLogic: SomeApi {
    override fun ping(): String {
        return "pong"
    }
}

@RestController
class SomeController(
    private val someApi: SomeApi
) {
    @PostMapping("/ping")
    fun ping(): String {
        return someApi.ping()
    }
}

class SomeModuleImpl: ContextModule {
    override fun apply(builder: ContextBuilder) {
        builder.setImpl(SomeApi::class.java, SomeApiLogic::class.java)
    }
}

val SOME_MODULE_WEB_SERVER = SpringWebServerModule(
    SomeModuleImpl(),
    listOf(
        SomeController::class.java
    )
)

class ControllerIntegrationTest {
    @Test
    fun `should use controller added as class in arch context`() {
        val context = SpringWebApp.run(
            modules = listOf(
                SOME_MODULE_WEB_SERVER
            ),
            useRandomPort = true
        )

        RestAssured.port = context.port
        RestAssured
            .given()
            .`when`()
            .post("/ping")
            .then()
            .statusCode(200)
            .body(equalTo("pong"))
    }
}
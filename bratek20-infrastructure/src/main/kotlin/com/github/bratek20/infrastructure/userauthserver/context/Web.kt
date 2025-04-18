// DO NOT EDIT! Autogenerated by HLA tool

package com.github.bratek20.infrastructure.userauthserver.context

import com.github.bratek20.architecture.context.api.ContextBuilder
import com.github.bratek20.architecture.context.api.ContextModule
import com.github.bratek20.infrastructure.httpclient.api.HttpClientConfig
import com.github.bratek20.infrastructure.httpserver.api.WebServerModule

import com.github.bratek20.infrastructure.userauthserver.api.*
import com.github.bratek20.infrastructure.userauthserver.impl.UserSessionLogic
import com.github.bratek20.infrastructure.userauthserver.web.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.context.annotation.SessionScope
import javax.servlet.http.HttpSession

class UserAuthServerWebClient(
    private val config: HttpClientConfig
): ContextModule {
    override fun apply(builder: ContextBuilder) {
        builder
            .setImplObject(UserAuthServerWebClientConfig::class.java, UserAuthServerWebClientConfig(config))
            .setImpl(UserAuthServerApi::class.java, UserAuthServerApiWebClient::class.java)
    }
}

@Configuration
class UserSessionConfig {
    @Bean
    @SessionScope
    fun userSession(session: HttpSession): UserSession {
        return UserSessionLogic(session)
    }
}

class UserAuthServerWebServer: WebServerModule {
    override fun apply(builder: ContextBuilder) {
        builder.withModule(UserAuthServerImpl())
    }

    override fun getControllers(): List<Class<*>> {
        return listOf(
            UserAuthServerApiController::class.java,
        )
    }

    override fun getConfigs(): List<Class<*>> {
        return listOf(
            UserSessionConfig::class.java,
        )
    }
}
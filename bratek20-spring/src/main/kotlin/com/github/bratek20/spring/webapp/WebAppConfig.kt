package com.github.bratek20.spring.webapp

import com.github.bratek20.infrastructure.httpserver.api.PassingExceptionHandler
import com.github.bratek20.logs.api.Logger
import com.github.bratek20.spring.config.BaseConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(
    BaseConfig::class,
)
internal open class WebAppConfig {
    @Bean
    open fun healthController(): HealthController {
        return HealthController()
    }

    @Bean
    open fun passingExceptionHandler(logger: Logger): PassingExceptionHandler {
        return PassingExceptionHandler(logger)
    }
}

package com.github.bratek20.spring.webapp

import com.github.bratek20.spring.config.BaseConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(
    BaseConfig::class
)
internal open class WebAppConfig

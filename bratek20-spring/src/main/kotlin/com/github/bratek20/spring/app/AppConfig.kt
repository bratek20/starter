package com.github.bratek20.spring.app

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import com.github.bratek20.spring.config.BaseConfig

@Configuration
@Import(
    BaseConfig::class
)
internal open class AppConfig

package pl.bratek20.spring.app

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import pl.bratek20.spring.config.BaseConfig

@Configuration
@Import(
    BaseConfig::class
)
open class SpringAppConfig

package com.github.bratek20.infrastructure.userauthserver.context

import org.springframework.stereotype.Component
import org.springframework.web.context.annotation.SessionScope

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Component
@SessionScope
annotation class SessionComponent
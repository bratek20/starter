package com.github.bratek20.infrastructure.sessionuser

import org.springframework.stereotype.Component
import org.springframework.web.context.annotation.SessionScope

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Component
@SessionScope
annotation class SessionComponent
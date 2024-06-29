package com.github.bratek20.spring.webapp

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/health")
internal class HealthController {
    @GetMapping
    fun get(): String {
        return "OK"
    }
}

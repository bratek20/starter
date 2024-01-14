package pl.bratek20.spring.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
class HealthController {

    @GetMapping
    public String get() {
        return "OK";
    }
}

package pl.bratek20.architecture.context.spring

import org.springframework.context.annotation.AnnotationConfigApplicationContext
import pl.bratek20.architecture.context.api.Context

class SpringContext(private val value: AnnotationConfigApplicationContext): Context {
    override fun <T> get(type: Class<T>): T {
        return value.getBean(type)
    }

}
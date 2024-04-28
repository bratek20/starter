package pl.bratek20.architecture.context.spring

import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import pl.bratek20.architecture.context.impl.AbstractContext

class SpringContext(vararg configurations: Class<*>): AbstractContext() {
    private val value: ConfigurableApplicationContext = AnnotationConfigApplicationContext(*configurations)

    override fun <T> getMany(type: Class<T>): List<T> {
        return value.getBeansOfType(type).values.toList()
    }
}
package pl.bratek20.architecture.context

import pl.bratek20.architecture.context.api.ContextBuilder
import pl.bratek20.architecture.context.guice.GuiceContextBuilder
import pl.bratek20.architecture.context.spring.SpringContextBuilder

fun someContextBuilder(): ContextBuilder {
    val randomInt = (0..100).random()
    return if (randomInt % 2 == 0)
        SpringContextBuilder() else GuiceContextBuilder()
}
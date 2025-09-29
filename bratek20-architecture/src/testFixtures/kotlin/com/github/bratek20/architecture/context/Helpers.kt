package com.github.bratek20.architecture.context

import com.github.bratek20.architecture.context.api.ContextBuilder
import com.github.bratek20.architecture.context.guice.GuiceContextBuilder
import com.github.bratek20.architecture.context.spring.SpringContextBuilder

fun someContextBuilder(seed: Int? = null): ContextBuilder {
    val randomInt = seed ?: (0..100).random()
    return if (randomInt % 2 == 0)
        SpringContextBuilder() else GuiceContextBuilder()
}

fun stableContextBuilder(): ContextBuilder {
    return SpringContextBuilder()
}
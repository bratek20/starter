package com.github.bratek20.infrastructure.httpclient.context

import com.github.bratek20.architecture.context.api.ContextBuilder
import com.github.bratek20.architecture.context.api.ContextModule

import com.github.bratek20.infrastructure.httpclient.api.*
import com.github.bratek20.infrastructure.httpclient.impl.*

class HttpClientBaseImpl: ContextModule {
    override fun apply(builder: ContextBuilder) {
        builder
            .setImpl(HttpClientFactory::class.java, HttpClientFactoryLogic::class.java)
    }
}

class HttpClientImpl: ContextModule {
    override fun apply(builder: ContextBuilder) {
        builder
            .withModule(HttpClientBaseImpl())
            .setImpl(HttpRequester::class.java, HttpRequesterLogic::class.java)
    }
}
package com.github.bratek20.infrastructure.httpclient.api

interface HttpResponse {
    val statusCode: Int

    fun <T> getBody(clazz: Class<T>?): T
}

interface HttpClient {
    fun get(path: String?): HttpResponse?
    fun <T> post(path: String?, body: T): HttpResponse?
}
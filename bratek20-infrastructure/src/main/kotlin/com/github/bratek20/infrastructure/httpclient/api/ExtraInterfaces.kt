package com.github.bratek20.infrastructure.httpclient.api

interface HttpResponse {
    fun getStatusCode(): Int
    fun <T> getBody(clazz: Class<T>): T
}

interface HttpClient {
    fun get(path: String): HttpResponse
    fun post(path: String, body: Any?): HttpResponse
}
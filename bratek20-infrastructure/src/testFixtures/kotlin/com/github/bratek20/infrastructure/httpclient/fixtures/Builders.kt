// DO NOT EDIT! Autogenerated by HLA tool

package com.github.bratek20.infrastructure.httpclient.fixtures

import com.github.bratek20.infrastructure.httpclient.api.*

data class HttpClientAuthDef(
    var username: String = "someValue",
    var password: String = "someValue",
)
fun httpClientAuth(init: HttpClientAuthDef.() -> Unit = {}): HttpClientAuth {
    val def = HttpClientAuthDef().apply(init)
    return HttpClientAuth.create(
        username = def.username,
        password = def.password,
    )
}

data class HttpClientConfigDef(
    var baseUrl: String = "someValue",
    var auth: (HttpClientAuthDef.() -> Unit)? = null,
    var persistSession: Boolean = false,
)
fun httpClientConfig(init: HttpClientConfigDef.() -> Unit = {}): HttpClientConfig {
    val def = HttpClientConfigDef().apply(init)
    return HttpClientConfig.create(
        baseUrl = def.baseUrl,
        auth = def.auth?.let { it -> httpClientAuth(it) },
        persistSession = def.persistSession,
    )
}

data class HttpHeaderDef(
    var key: String = "someValue",
    var value: String = "someValue",
)
fun httpHeader(init: HttpHeaderDef.() -> Unit = {}): HttpHeader {
    val def = HttpHeaderDef().apply(init)
    return HttpHeader.create(
        key = def.key,
        value = def.value,
    )
}

data class HttpRequestDef(
    var url: String = "someValue",
    var method: String = HttpMethod.GET.name,
    var content: String? = null,
    var contentType: String = "someValue",
    var headers: List<(HttpHeaderDef.() -> Unit)> = emptyList(),
)
fun httpRequest(init: HttpRequestDef.() -> Unit = {}): HttpRequest {
    val def = HttpRequestDef().apply(init)
    return HttpRequest.create(
        url = def.url,
        method = HttpMethod.valueOf(def.method),
        content = def.content,
        contentType = def.contentType,
        headers = def.headers.map { it -> httpHeader(it) },
    )
}

data class SendResponseDef(
    var statusCode: Int = 0,
    var body: String = "someValue",
    var headers: List<(HttpHeaderDef.() -> Unit)> = emptyList(),
)
fun sendResponse(init: SendResponseDef.() -> Unit = {}): SendResponse {
    val def = SendResponseDef().apply(init)
    return SendResponse.create(
        statusCode = def.statusCode,
        body = def.body,
        headers = def.headers.map { it -> httpHeader(it) },
    )
}
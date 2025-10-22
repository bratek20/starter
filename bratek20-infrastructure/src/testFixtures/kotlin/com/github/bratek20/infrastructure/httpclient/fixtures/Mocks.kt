package com.github.bratek20.infrastructure.httpclient.fixtures

import com.github.bratek20.infrastructure.httpclient.api.HttpRequest
import com.github.bratek20.infrastructure.httpclient.api.HttpRequester
import com.github.bratek20.infrastructure.httpclient.api.SendResponse

class HttpRequesterMock: HttpRequester {
    private var lastRequest: HttpRequest? = null

    var response: SendResponseDef.() -> Unit = {}

    override fun send(request: HttpRequest): SendResponse {
        lastRequest = request
        return sendResponse(response)
    }

    fun assertLastRequest(expected: ExpectedHttpRequest.() -> Unit) {
        assertHttpRequest(lastRequest!!, expected)
    }
}
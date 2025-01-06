package com.github.bratek20.logs.api

interface LogsApi {
    fun addErrorListener(onError: (message: String) -> Unit)
}
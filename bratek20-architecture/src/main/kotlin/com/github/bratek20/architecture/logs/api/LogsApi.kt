package com.github.bratek20.architecture.logs.api

interface LogsApi {
    fun addErrorListener(onError: (message: String) -> Unit)
}
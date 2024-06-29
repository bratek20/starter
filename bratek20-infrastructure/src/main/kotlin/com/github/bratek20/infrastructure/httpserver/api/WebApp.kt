package com.github.bratek20.infrastructure.httpserver.api

interface WebApp {
    fun run(): WebAppContext
}
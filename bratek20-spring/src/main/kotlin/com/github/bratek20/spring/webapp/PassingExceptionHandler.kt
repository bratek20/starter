package com.github.bratek20.spring.webapp

import com.github.bratek20.architecture.exceptions.ApiException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class PassingExceptionHandler {
    @ExceptionHandler(ApiException::class)
    fun handleException(ex: ApiException, request: WebRequest?): ResponseEntity<String> {
        val exceptionType = getExceptionType(ex)
        //log.warn("Passing exception of type: {}, message: {}", exceptionType, ex.message)

        val message = """
            {
                "passedException": {
                    "type": "$exceptionType",
                }
            }
            """.trimIndent()
        return ResponseEntity(message, HttpStatus.OK)
    }

    companion object {
        fun getExceptionType(ex: RuntimeException): String {
            return ex.javaClass.simpleName
        }
    }
}
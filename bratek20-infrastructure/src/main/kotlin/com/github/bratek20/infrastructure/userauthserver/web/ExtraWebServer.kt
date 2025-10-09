package com.github.bratek20.infrastructure.userauthserver.web

import com.github.bratek20.architecture.structs.api.Struct
import com.github.bratek20.architecture.serialization.api.Serializer
import com.github.bratek20.architecture.serialization.context.SerializationFactory

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import com.github.bratek20.infrastructure.userauthserver.api.*
import javax.servlet.http.HttpSession

@RestController
@RequestMapping("/b20/userAuthServerApi")
class UserAuthServerApiController(
    private val api: UserAuthServerApi,
) {
    private val serializer: Serializer = SerializationFactory.createSerializer()

    @PostMapping("/createUserAndLogin")
    fun createUserAndLogin(session: HttpSession): Struct {
        val userMapping = api.createUserAndLogin()
        session.setAttribute("userId", userMapping.getUserId().value)
        return serializer.asStruct(UserAuthServerApiCreateUserAndLoginResponse(userMapping))
    }

    @PostMapping("/login")
    fun login(@RequestBody rawRequest: Struct, session: HttpSession): Struct {
        val request = serializer.fromStruct(rawRequest, UserAuthServerApiLoginRequest::class.java)
        val mapping = api.login(request.getAuthId())
        session.setAttribute("userId", mapping.getUserId().value)
        return serializer.asStruct(UserAuthServerApiLoginResponse(mapping))
    }
}


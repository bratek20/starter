package com.github.bratek20.architecture.users.impl

import com.github.bratek20.architecture.users.api.User
import com.github.bratek20.architecture.users.api.UserId

class InMemoryUser: User {
    private val id: UserId = UserId(1)

    override fun getId(): UserId {
        return id
    }
}
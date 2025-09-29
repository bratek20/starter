package com.github.bratek20.infrastructure.sessionuser

import com.github.bratek20.architecture.users.api.User
import com.github.bratek20.architecture.users.api.UserId
import com.github.bratek20.infrastructure.userauthserver.api.NotLoggedInException
import javax.servlet.http.HttpSession

class SessionUser(
    private val session: HttpSession
): User {
    override fun getId(): UserId {
        val userIdValue = session.getAttribute("userId") as Int?
            ?: throw NotLoggedInException("User is not logged in")
        return UserId(userIdValue)
    }
}
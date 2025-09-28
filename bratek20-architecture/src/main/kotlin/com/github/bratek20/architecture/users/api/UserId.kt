package com.github.bratek20.architecture.users.api

data class UserId(
    val value: Int
) {
    override fun toString(): String {
        return value.toString()
    }
}
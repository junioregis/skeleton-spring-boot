package com.domain.core.exception

class FacebookException(message: String, throwable: Throwable? = null) : Exception(message, throwable) {

    companion object {
        const val USER_NOT_FOUND = "User not found"
    }
}
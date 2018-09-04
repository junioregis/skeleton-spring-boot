package com.domain.core.exception

class AuthException(message: String, throwable: Throwable? = null) : Exception(message, throwable) {

    companion object {
        const val PROVIDER_NOT_PRESENT = "Provider not present"
        const val INVALID_PROVIDER = "Invalid provider"
        const val NOT_PRESENT = "Token not present"
        const val UNAUTHORIZED = "Unauthorized"
        const val FACEBOOK_ERROR = "Facebook error"
    }
}
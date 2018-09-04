package com.domain.core.exception

class JwtException(message: String, throwable: Throwable) : Exception(message, throwable) {

    companion object {
        const val INVALID_TOKEN = "Invalid token"
        const val GENERATOR_FAILS = "Generator fails"
    }
}
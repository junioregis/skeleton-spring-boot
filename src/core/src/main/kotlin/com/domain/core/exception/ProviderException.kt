package com.domain.core.exception

class ProviderException(message: String, throwable: Throwable? = null) : Exception(message, throwable) {

    companion object {
        const val NOT_FOUND = "Provider not found"
    }
}
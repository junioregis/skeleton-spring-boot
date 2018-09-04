package com.domain.core.exception

class SlackException(message: String, throwable: Throwable? = null) : Exception(message, throwable) {

    companion object {
        const val ERROR = "Error"
    }
}
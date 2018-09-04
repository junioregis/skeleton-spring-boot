package com.domain.core.service

import com.domain.core.config.SlackConfig
import com.domain.core.exception.SlackException
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class SlackService {

    @Autowired
    private lateinit var config: SlackConfig

    fun send(title: String, t: Throwable) {
        val message = buildErrorLines(t)

        send(title, message)
    }

    fun send(title: String, message: String) {
        val body = SlackRequestBody(config.token, config.channel, config.user, "*$title*\n\n$message")
        val json = ObjectMapper().writeValueAsString(body)

        val request = Request.Builder()
                .url(SLACK_PATH)
                .post(RequestBody.create(SLACK_MEDIA_TYPE, json))
                .build()

        val client = OkHttpClient()

        try {
            val response = client.newCall(request).execute()
            val code = response.code()

            if (code == 200) {
                response.body()?.close()
            }
        } catch (e: Exception) {
            throw SlackException(SlackException.ERROR, e)
        }
    }

    private fun buildErrorLines(t: Throwable): String {
        val lines = ArrayList<ErrorLine>()

        t.stackTrace.forEach {
            lines.add(ErrorLine(it.className, it.lineNumber, it.methodName))
        }

        return lines.joinToString("\n")
    }

    private data class ErrorLine(var className: String,
                                 var lineNumber: Int,
                                 var methodName: String) {

        override fun toString(): String {
            return "$className:$methodName (line $lineNumber)"
        }
    }

    private data class SlackRequestBody(val token: String,
                                        val channel: String,
                                        val username: String,
                                        val text: String)

    companion object {

        private const val SLACK_PATH = "https://slack.com/api/chat.postMessage"
        private val SLACK_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8")
    }
}
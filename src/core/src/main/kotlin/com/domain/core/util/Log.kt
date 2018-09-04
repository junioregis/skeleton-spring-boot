package com.domain.core.util

import com.domain.core.service.SlackService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Log {

    private val logger = LoggerFactory.getLogger(Log::class.java)

    @Autowired
    private lateinit var slack: SlackService

    fun d(t: Throwable) {
        println(t)

        val title = t.message ?: ""

        d(t.message ?: t.toString())

        slack.send(title, t)
    }

    fun e(t: Throwable) {
        println(t)

        val title = t.message ?: ""

        e(t.message ?: t.toString())

        sendSlackMessage(title, t)
    }

    fun d(body: String) {
        logger.debug(body)

        sendSlackMessage("Debug", body)
    }

    fun e(body: String) {
        logger.error(body)

        sendSlackMessage("Error", body)
    }

    fun sendSlackMessage(title: String, body: Any) {
        try {
            when (body) {
                is String -> slack.send(title, body)
                is Throwable -> slack.send(title, body)
            }
        } catch (e: Exception) {
            logger.error("Failed to send message to slack")
        }
    }
}
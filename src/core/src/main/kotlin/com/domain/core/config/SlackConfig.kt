package com.domain.core.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("slack")
class SlackConfig {

    var token: String = ""
    var user: String = ""
    var channel: String = ""
}
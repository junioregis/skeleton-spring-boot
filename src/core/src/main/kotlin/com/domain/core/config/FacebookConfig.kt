package com.domain.core.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("facebook")
class FacebookConfig {

    var id: String = ""
    var secret: String = ""
}
package com.domain.api.v1.controller.auth

import com.fasterxml.jackson.annotation.JsonProperty

class AuthRequest {

    @JsonProperty("provider")
    var provider: String = ""

    @JsonProperty("token")
    var token: String = ""
}
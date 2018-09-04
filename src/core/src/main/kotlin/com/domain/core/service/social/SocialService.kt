package com.domain.core.service.social

interface SocialService {

    fun getUser(accessToken: String): UserInfo

    companion object {
        val DEFAULT_IMAGE_SIZE = 512
    }
}
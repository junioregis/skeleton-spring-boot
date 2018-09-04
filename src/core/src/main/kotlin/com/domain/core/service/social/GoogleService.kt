package com.domain.core.service.social

import com.domain.core.exception.GoogleException
import org.springframework.social.google.api.impl.GoogleTemplate
import org.springframework.stereotype.Component

@Component
class GoogleService : SocialService {

    override fun getUser(accessToken: String): UserInfo {
        try {
            val template = GoogleTemplate(accessToken)
            val person = template.plusOperations().googleProfile

            val imageUrl = person.imageUrl
                    .replace(Regex("^(.+=)\\d+$"), "$1${SocialService.DEFAULT_IMAGE_SIZE}")

            return UserInfo(provider = Providers.GOOGLE,
                    id = person.id,
                    email = person.accountEmail,
                    name = person.displayName,
                    bithday = person.birthday,
                    gender = person.gender,
                    image = imageUrl)
        } catch (e: Exception) {
            throw GoogleException(GoogleException.USER_NOT_FOUND)
        }
    }
}
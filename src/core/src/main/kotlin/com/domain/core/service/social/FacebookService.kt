package com.domain.core.service.social

import com.domain.core.exception.FacebookException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.social.facebook.api.User
import org.springframework.social.facebook.api.impl.FacebookTemplate
import org.springframework.stereotype.Component
import java.text.SimpleDateFormat

@Component
class FacebookService : SocialService {

    override fun getUser(accessToken: String): UserInfo {
        try {
            val template = FacebookTemplate(accessToken)

            template.setApiVersion(API_VERSION)

            val fb = template.fetchObject("me", User::class.java, *FIELDS)

            val birthday = SimpleDateFormat("MM/dd/yyyy").parse(fb.birthday)

            val mapper = ObjectMapper()

            val extraData = mapper.writeValueAsString(fb.extraData.toMap())

            val image = mapper.readTree(extraData)["picture"]["data"]["url"].textValue()

            return UserInfo(provider = Providers.FACEBOOK,
                    id = fb.id,
                    email = fb.email,
                    name = fb.name,
                    bithday = birthday,
                    gender = fb.gender,
                    image = image)
        } catch (e: Exception) {
            throw FacebookException(FacebookException.USER_NOT_FOUND)
        }
    }

    companion object {

        private val API_VERSION = "3.1"
        private val FIELDS = arrayOf(
                "id",
                "email",
                "name",
                "birthday",
                "gender",
                "picture.width(${SocialService.DEFAULT_IMAGE_SIZE}).height(${SocialService.DEFAULT_IMAGE_SIZE})")
    }
}
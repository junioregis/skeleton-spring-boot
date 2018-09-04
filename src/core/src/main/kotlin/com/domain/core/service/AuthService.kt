package com.domain.core.service

import com.domain.core.entity.User
import com.domain.core.exception.AuthException
import com.domain.core.exception.JwtException
import com.domain.core.repository.UserRepository
import com.domain.core.service.social.FacebookService
import com.domain.core.service.social.GoogleService
import com.domain.core.service.social.Providers
import com.domain.core.service.social.UserInfo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.util.*

@Component
class AuthService {

    @Autowired
    private lateinit var manager: AuthenticationManager

    @Autowired
    private lateinit var facebook: FacebookService

    @Autowired
    private lateinit var google: GoogleService

    @Autowired
    private lateinit var jwt: JwtService

    @Autowired
    private lateinit var userRepository: UserRepository

    fun auth(token: String) {
        val userId = try {
            jwt.verify(token)
        } catch (e: JwtException) {
            throw AuthException(AuthException.UNAUTHORIZED)
        }

        val user = getUser(userId)

        if (user.token != token) {
            throw AuthException(AuthException.UNAUTHORIZED)
        } else {
            val auth = manager.authenticate(
                    UsernamePasswordAuthenticationToken(
                            user,
                            null,
                            arrayListOf(SimpleGrantedAuthority("USER"))))

            SecurityContextHolder.getContext().authentication = auth
        }
    }

    fun genToken(user: User): String {
        return jwt.generate(user)
    }

    fun getUser(provider: String, accessToken: String): UserInfo {
        return when (provider) {
            Providers.FACEBOOK.value -> facebook.getUser(accessToken)
            Providers.GOOGLE.value -> google.getUser(accessToken)
            else -> {
                throw AuthException(AuthException.INVALID_PROVIDER)
            }
        }
    }

    private fun getUser(id: String): User {
        return userRepository.findById(UUID.fromString(id)).orElseThrow {
            AuthException(AuthException.UNAUTHORIZED)
        }
    }
}
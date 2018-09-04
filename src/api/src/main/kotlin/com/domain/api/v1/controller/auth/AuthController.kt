package com.domain.api.v1.controller.auth

import com.domain.api.v1.controller.AppController
import com.domain.core.doc.annotation.Doc
import com.domain.core.doc.annotation.DocAuthenticated
import com.domain.core.doc.annotation.DocRequest
import com.domain.core.doc.annotation.DocResponse
import com.domain.core.entity.User
import com.domain.core.entity.converter.GenderConverter
import com.domain.core.exception.AuthException
import com.domain.core.repository.UserRepository
import com.domain.core.response.Body
import com.domain.core.response.Response
import com.domain.core.service.AuthService
import com.domain.core.service.social.UserInfo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
@Doc(name = "auth")
class AuthController : AppController() {

    @Autowired
    private lateinit var authService: AuthService

    @Autowired
    private lateinit var userRepository: UserRepository

    @PostMapping
    @Doc(summary = "Auth",
            body = DocRequest(type = AuthRequest::class),
            responses = [
                DocResponse(status = 200, message = "auth.success", description = "SignUp or signIn successful"),
                DocResponse(status = 401, message = "auth.error", description = "Auth error")],
            authenticated = DocAuthenticated.FALSE)
    fun auth(@RequestBody(required = true) body: AuthRequest): ResponseEntity<Body> {
        return try {
            val userInfo = authService.getUser(body.provider, body.token)
            val user = userRepository.findByEmail(userInfo.email)
                    .orElse(User())

            val isRegistration = user.id == null

            if (isRegistration) {
                signUp(user, userInfo)
            } else {
                signIn(user, userInfo)
            }

            val type = if (isRegistration) "signUp" else "signIn"

            val headers = HttpHeaders()

            headers.add("Auth-Token", user.token)
            headers.add("Auth-Type", type)

            Response.Builder()
                    .code(200)
                    .headers(headers)
                    .message("auth.success")
                    .build()
        } catch (e: AuthException) {
            Response.Builder()
                    .code(401)
                    .message("auth.error")
                    .build()
        }
    }

    @GetMapping("/logout")
    @Doc(summary = "Do logout of all devices",
            responses = [
                DocResponse(status = 200, message = "auth.logout.success", description = "Logout successful"),
                DocResponse(status = 401, message = "auth.logout.error", description = "Logout error")])
    fun logout(): ResponseEntity<Body> {
        return try {
            currentUser.token = ""

            userRepository.save(currentUser)

            Response.Builder()
                    .code(200)
                    .message("auth.logout.success")
                    .build()
        } catch (e: AuthException) {
            Response.Builder()
                    .code(401)
                    .message("auth.logout.error")
                    .build()
        }
    }

    private fun signUp(user: User, userInfo: UserInfo): User {
        update(user, userInfo)

        userRepository.save(user)

        user.token = authService.genToken(user)

        return userRepository.save(user)
    }

    private fun signIn(user: User, userInfo: UserInfo): User {
        update(user, userInfo)

        if (user.token.isEmpty()) {
            user.token = authService.genToken(user)
        }

        return userRepository.save(user)
    }

    fun update(user: User, info: UserInfo) {
        // user
        user.provider = info.provider
        user.providerId = info.id
        user.email = info.email

        // preference
        user.preference.user = user

        // profile
        user.profile.user = user
        user.profile.name = info.name
        user.profile.gender = GenderConverter().convertToEntityAttribute(info.gender)
        user.profile.image = info.image
    }
}
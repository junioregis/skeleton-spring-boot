package com.domain.core.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTCreationException
import com.domain.core.config.AppConfig
import com.domain.core.entity.User
import com.domain.core.exception.JwtException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtService {

    @Autowired
    private lateinit var appConfig: AppConfig

    fun generate(user: User): String {
        return try {
            JWT.create()
                    .withIssuer(user.id.toString())
                    .withSubject(UUID.randomUUID().toString())
                    .sign(algorithm())
        } catch (e: JWTCreationException) {
            throw JwtException(JwtException.GENERATOR_FAILS, e)
        }
    }

    fun verify(token: String): String {
        return try {
            JWT.require(algorithm())
                    .build()
                    .verify(token)
                    .issuer
        } catch (e: Exception) {
            throw JwtException(JwtException.INVALID_TOKEN, e)
        }
    }

    @Bean
    fun algorithm(): Algorithm {
        return Algorithm.HMAC512(appConfig.secret)
    }
}
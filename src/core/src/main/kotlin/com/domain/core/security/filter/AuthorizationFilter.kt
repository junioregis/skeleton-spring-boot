package com.domain.core.security.filter

import com.domain.core.exception.AuthException
import com.domain.core.service.AuthService
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthorizationFilter(private val authService: AuthService) : OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val auth = request.getHeader(HEADER_NAME)

        if (auth == null || !auth.startsWith(HEADER_VALUE_PREFIX)) {
            chain.doFilter(request, response)
            return
        }

        val token = auth.replace(HEADER_VALUE_PREFIX, "")

        try {
            authService.auth(token)
        } catch (e: AuthException) {
            throw BadCredentialsException("Invalid token")
        }

        chain.doFilter(request, response)
    }

    companion object {

        const val HEADER_NAME = "Authorization"
        const val HEADER_VALUE_PREFIX = "Bearer "
    }
}
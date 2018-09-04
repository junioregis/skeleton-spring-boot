package com.domain.core.security

import com.domain.core.response.Response
import com.domain.core.security.filter.AuthorizationFilter
import com.domain.core.service.AuthService
import com.domain.core.util.Log
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.Authentication
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.stereotype.Component

@Component
@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    private lateinit var log: Log

    @Autowired
    private lateinit var authService: AuthService

    override fun configure(http: HttpSecurity) {
        // Requests
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/auth").permitAll()
                .antMatchers(HttpMethod.GET, "/docs/**").permitAll()
                .anyRequest().authenticated()

        // Filters
        http.addFilterAt(AuthorizationFilter(authService), UsernamePasswordAuthenticationFilter::class.java)

        // Stateless
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        // Cors
        http.cors()

        // CSRF
        http.csrf().disable()

        // Exception Handling
        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint())
                .accessDeniedHandler(accessEntryPoint())
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(object : AuthenticationProvider {
            override fun authenticate(authentication: Authentication) = authentication

            override fun supports(authentication: Class<*>) = authentication == UsernamePasswordAuthenticationToken::class.java
        })
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Bean
    fun authenticationEntryPoint(): AuthenticationEntryPoint {
        return AuthenticationEntryPoint { request, response, exception ->
            log.d(exception)
            Response.write(response, 401, "auth.error.unauthorized")
        }
    }

    @Bean
    fun accessEntryPoint(): AccessDeniedHandler {
        return AccessDeniedHandler { request, response, exception ->
            log.e(exception)
            Response.write(response, 500, "error.internal")
        }
    }
}
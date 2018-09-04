package com.domain.core

import com.domain.core.util.Log
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.core.env.Environment
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor
import org.springframework.web.servlet.i18n.SessionLocaleResolver
import java.util.*

@SpringBootApplication
class BaseApplication : WebMvcConfigurer {

    @Autowired
    protected lateinit var log: Log

    @Autowired
    protected lateinit var context: ApplicationContext

    @Autowired
    protected lateinit var env: Environment

    @Bean
    fun localeResolver(): LocaleResolver {
        val resolver = SessionLocaleResolver()

        resolver.setDefaultLocale(Locale.ROOT)

        return resolver
    }

    @Bean
    fun localeInterceptor(): LocaleChangeInterceptor {
        val interceptor = LocaleChangeInterceptor()

        interceptor.paramName = "lang"

        return interceptor
    }



    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(localeInterceptor())
    }
}
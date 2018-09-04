package com.domain.core.response

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletResponse

@Component
class Response(var code: Int = 200,
               var message: String = "",
               var result: Any? = null,
               var headers: HttpHeaders = HttpHeaders()) {

    @Bean
    private fun messageSource(): MessageSource {
        val source = ReloadableResourceBundleMessageSource()

        source.setBasename("classpath:locale/messages")
        source.setDefaultEncoding("UTF-8")

        return source
    }

    fun build(): ResponseEntity<Body> {
        val status = HttpStatus.valueOf(code)
        val meta = Meta(status.value(), messageSource().getMessage(message, arrayOf(), LocaleContextHolder.getLocale()))
        val body = Body(meta, result)

        return ResponseEntity(body, headers, status)
    }

    class Builder {

        private val instance = Response()

        fun code(code: Int) = apply { instance.code = code }

        fun message(message: String) = apply { instance.message = message }

        fun result(result: Any?) = apply { instance.result = result }

        fun headers(headers: HttpHeaders) = apply { instance.headers = headers }

        fun build() = instance.build()
    }

    companion object {

        fun write(response: HttpServletResponse, code: Int, message: String) {
            val r = Response.Builder()
                    .code(code)
                    .message(message)
                    .build()

            val json = ObjectMapper().writeValueAsString(r.body)

            response.status = code
            response.contentType = "application/json; charset=utf-8"
            response.writer.write(json)
            response.writer.close()
        }
    }
}

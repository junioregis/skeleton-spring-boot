package com.domain.core.mapping

import com.domain.core.annotation.ApiVersion
import com.domain.core.doc.DocBuilder
import org.springframework.core.annotation.AnnotationUtils
import org.springframework.web.servlet.mvc.condition.*
import org.springframework.web.servlet.mvc.method.RequestMappingInfo
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping
import java.lang.reflect.Method

class ApiMapping : RequestMappingHandlerMapping() {

    val docBuilder = DocBuilder()

    override fun getMappingForMethod(method: Method, handlerType: Class<*>): RequestMappingInfo? {
        var info = super.getMappingForMethod(method, handlerType) ?: return null

        val apiAnnotation = AnnotationUtils.findAnnotation(handlerType, ApiVersion::class.java)

        if (apiAnnotation != null) {
            val version = apiAnnotation.value
            val condition = getCustomMethodCondition(method)

            info = build(version, condition).combine(info)

            docBuilder.addEndpoint(version, method, info)
        }

        return info
    }

    private fun build(version: Int, condition: RequestCondition<*>?): RequestMappingInfo {
        val patterns = PatternsRequestCondition(
                arrayOf(),
                urlPathHelper,
                pathMatcher,
                useSuffixPatternMatch(),
                useTrailingSlashMatch(),
                fileExtensions)

        val headers = "Api-Version=$version"

        return RequestMappingInfo(
                patterns,
                RequestMethodsRequestCondition(),
                ParamsRequestCondition(),
                HeadersRequestCondition(headers),
                ConsumesRequestCondition(),
                ProducesRequestCondition(),
                condition)
    }
}
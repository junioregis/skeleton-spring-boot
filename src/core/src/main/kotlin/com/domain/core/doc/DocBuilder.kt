package com.domain.core.doc

import com.domain.core.doc.annotation.Doc
import com.domain.core.doc.annotation.DocAuthenticated
import com.domain.core.doc.annotation.DocRequest
import com.domain.core.doc.annotation.DocResponse
import com.domain.core.doc.builder.DocBase
import com.domain.core.doc.builder.postman.DocPostman
import com.domain.core.security.filter.AuthorizationFilter
import org.springframework.core.annotation.AnnotationUtils
import org.springframework.http.HttpHeaders
import org.springframework.web.servlet.mvc.method.RequestMappingInfo
import java.lang.reflect.Method
import kotlin.reflect.full.superclasses

class DocBuilder {

    val docs: HashMap<Int, ArrayList<DocEndpoint>> = hashMapOf()

    fun addEndpoint(version: Int, method: Method, info: RequestMappingInfo) {
        var controllerName = ""
        var methodName = ""

        val headers = HttpHeaders()

        val params = arrayListOf<DocRequest>()
        var body: DocRequest? = null
        val responses = arrayListOf<DocResponse>()

        val authHeaderValue = "${AuthorizationFilter.HEADER_VALUE_PREFIX}<token>"

        var controllerAnnotation = AnnotationUtils.getAnnotation(method.declaringClass, Doc::class.java)

        if (controllerAnnotation != null) {
            controllerName = controllerAnnotation.name

            params.addAll(controllerAnnotation.params.asList())
            responses.addAll(controllerAnnotation.responses.asList())
        }

        for (c in method.declaringClass.kotlin.superclasses) {
            controllerAnnotation = AnnotationUtils.getAnnotation(c.java, Doc::class.java)

            if (controllerAnnotation != null) {
                if (controllerName.isEmpty()) {
                    controllerName = controllerAnnotation.name
                }

                params.addAll(controllerAnnotation.params.asList())
                responses.addAll(controllerAnnotation.responses.asList())

                if (controllerAnnotation.authenticated == DocAuthenticated.TRUE) {
                    headers.add(AuthorizationFilter.HEADER_NAME, authHeaderValue)
                }
            }
        }

        val methodAnnotation = AnnotationUtils.getAnnotation(method, Doc::class.java)

        if (methodAnnotation != null) {
            methodName = methodAnnotation.name

            params.addAll(methodAnnotation.params.asList())
            if (methodAnnotation.body.type != Void::class) {
                body = methodAnnotation.body
            }

            responses.addAll(methodAnnotation.responses.asList())
        }

        if (responses.isEmpty()) {
            return
        }

        for (e in info.headersCondition.expressions) {
            headers.add(e.name, e.value)
        }

        when (methodAnnotation?.authenticated) {
            DocAuthenticated.TRUE -> headers.add(AuthorizationFilter.HEADER_NAME, authHeaderValue)
            DocAuthenticated.FALSE -> headers.remove(AuthorizationFilter.HEADER_NAME)
            else -> {
            }
        }

        val endpoint = DocEndpoint()

        endpoint.controllerName = controllerName
        endpoint.methodName = methodName
        endpoint.patterns = info.patternsCondition.patterns.map { it.toString() }
        endpoint.summary = methodAnnotation?.summary ?: ""
        endpoint.controller = method.declaringClass.kotlin
        endpoint.methods = info.methodsCondition.methods.map { it.toString() }
        endpoint.headers = headers
        endpoint.params = if (params.any()) params else null
        endpoint.body = body
        endpoint.responses = responses

        if (docs[version] == null) {
            docs[version] = ArrayList()
        }

        docs[version]?.add(endpoint)
    }

    inline fun build(title: String,
                     docVersion: String,
                     domain: String,
                     action: (List<DocBase>) -> Unit) {
        val result = arrayListOf<DocBase>()

        docs.forEach {
            result.add(DocPostman(title, it.key, docVersion, domain, it.value.toTypedArray()))
        }

        action(result)
    }
}
package com.domain.core.doc

import com.domain.core.doc.annotation.DocRequest
import com.domain.core.doc.annotation.DocResponse
import org.springframework.http.HttpHeaders
import kotlin.reflect.KClass

class DocEndpoint(var controllerName: String = "",
                  var methodName: String = "",
                  var patterns: List<String> = arrayListOf(),
                  var summary: String = "",
                  var controller: KClass<*> = Void::class,
                  var methods: List<String> = arrayListOf(),
                  var headers: HttpHeaders = HttpHeaders(),
                  var params: List<DocRequest>? = null,
                  var body: DocRequest? = null,
                  var responses: List<DocResponse> = arrayListOf())
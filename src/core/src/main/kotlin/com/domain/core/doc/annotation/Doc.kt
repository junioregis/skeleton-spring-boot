package com.domain.core.doc.annotation

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Doc(val name: String = "",
                     val summary: String = "",
                     val params: Array<DocRequest> = [],
                     val body: DocRequest = DocRequest(),
                     val responses: Array<DocResponse> = [],
                     val authenticated: DocAuthenticated = DocAuthenticated.NULL)
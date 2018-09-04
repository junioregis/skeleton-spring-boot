package com.domain.core.doc.annotation

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class DocHeader(val key: String = "",
                           val description: String = "")
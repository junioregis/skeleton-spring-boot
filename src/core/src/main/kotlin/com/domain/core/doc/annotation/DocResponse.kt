package com.domain.core.doc.annotation

import kotlin.reflect.KClass

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class DocResponse(val status: Int = 200,
                             val message: String = "",
                             val description: String = "",
                             val headers: Array<DocHeader> = [],
                             val body: KClass<*> = Void::class)
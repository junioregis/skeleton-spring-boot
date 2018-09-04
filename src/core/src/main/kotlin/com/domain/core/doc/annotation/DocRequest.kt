package com.domain.core.doc.annotation

import kotlin.reflect.KClass

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class DocRequest(val name: String = "",
                            val type: KClass<*> = Void::class,
                            val description: String = "",
                            val required: Boolean = true)
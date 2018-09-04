package com.domain.core.config

import com.domain.core.doc.builder.DocBase
import com.domain.core.mapping.ApiMapping
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerMapping
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport

@Component
class WebMvcConfig : WebMvcConfigurationSupport() {

    @Value("\${spring.profiles.active}")
    private lateinit var profile: String

    @Value("\${server.port}")
    private lateinit var port: String

    private val docs = hashMapOf<String, ArrayList<DocBase>>()

    override fun requestMappingHandlerMapping() = ApiMapping()

    override fun viewControllerHandlerMapping(): HandlerMapping {
        val apiMapping = applicationContext!!.getBean(ApiMapping::class.java)

        var title = "Title"
        val version = "1.0.0"
        val domain = if (profile == "dev") {
            title = "${profile.toUpperCase()} - $title"

            "http://localhost:$port"
        } else {
            "https://api.domain.com"
        }

        apiMapping.docBuilder.build(title, version, domain) {
            it.forEach { doc ->
                if (docs[doc.label] == null) {
                    docs[doc.label] = ArrayList()
                }

                docs[doc.label]!!.add(doc)
            }
        }

        return super.viewControllerHandlerMapping()
    }

    @Bean
    fun docs(): Map<String, List<DocBase>> = docs
}
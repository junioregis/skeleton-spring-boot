package com.domain.core.controller.docs

import com.domain.core.doc.builder.DocBase
import com.domain.core.doc.builder.postman.DocPostman
import javassist.tools.web.BadHttpRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/docs")
class DocsController {

    @Autowired
    private lateinit var docs: Map<String, List<DocBase>>

    @GetMapping("/{type}/v{version}")
    fun doc(@PathVariable(value = "type", required = true) type: String,
            @PathVariable(value = "version", required = true) version: Int): ResponseEntity<String> {
        val document = docs[type]
                ?.first { it.apiVersion == version }
                ?: throw BadHttpRequest()

        val headers = HttpHeaders()

        headers["Content-Type"] = when (document.label) {
            DocPostman.LABEL -> "application/json"
            else -> "application/text"
        }

        return ResponseEntity.ok()
                .headers(headers)
                .body(document.content)
    }
}
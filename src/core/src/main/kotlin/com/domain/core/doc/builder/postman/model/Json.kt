package com.domain.core.doc.builder.postman.model

import com.fasterxml.jackson.databind.ObjectMapper

class Json(val info: Info,
           val item: HashSet<Item> = hashSetOf()) {

    override fun toString(): String {
        return ObjectMapper()
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(this)
    }
}
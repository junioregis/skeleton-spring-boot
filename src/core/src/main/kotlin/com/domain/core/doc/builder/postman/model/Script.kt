package com.domain.core.doc.builder.postman.model

import java.util.*

class Script(val id: UUID = UUID.randomUUID(),
             val type: String = SCRIPT_TYPE,
             val exec: ArrayList<String> = arrayListOf()) {

    companion object {
        const val SCRIPT_TYPE = "text/javascript"
    }
}
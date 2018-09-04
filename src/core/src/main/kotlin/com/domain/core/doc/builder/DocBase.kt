package com.domain.core.doc.builder

import com.domain.core.doc.DocEndpoint

abstract class DocBase(val label: String = "",
                       var title: String = "",
                       var apiVersion: Int = 0,
                       var docVersion: String = "",
                       var domain: String = "",
                       var endpoints: Array<DocEndpoint> = arrayOf()) {

    abstract val content: String

    val protocol: String
        get() {
            return Regex("^(http|https).+")
                    .find(domain)
                    ?.groupValues
                    ?.get(1) ?: ""
        }

    val host: String
        get() {
            return Regex("^(http|https)://(.+)/?")
                    .find(domain)
                    ?.groupValues
                    ?.get(2) ?: ""
        }

    val port: String
        get() {
            return Regex("^(http|https)://(.+):(\\d+)/")
                    .find(domain)
                    ?.groupValues
                    ?.get(3) ?: ""
        }

    val path: String
        get() {
            return "$protocol://$host:$port"
        }
}
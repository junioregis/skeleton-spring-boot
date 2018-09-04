package com.domain.core.doc.builder.postman

import com.domain.core.doc.DocEndpoint
import com.domain.core.doc.builder.DocBase
import com.domain.core.doc.builder.postman.model.*
import com.domain.core.security.filter.AuthorizationFilter
import com.fasterxml.jackson.databind.ObjectMapper
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

class DocPostman(title: String,
                 apiVersion: Int,
                 docVersion: String,
                 domain: String,
                 endpoints: Array<DocEndpoint>) : DocBase(LABEL, title, apiVersion, docVersion, domain, endpoints) {

    override val content = build()

    private val authVarName: String
        get() {
            return "${title}_v${apiVersion}_${docVersion}_auth"
                    .replace(Regex("([^A-Za-z0-9]+)|(\\s+)"), "_")
                    .toLowerCase()
        }

    private fun build(): String {
        val info = Info(name = "$title (v$apiVersion) - $docVersion")
        val json = Json(info)

        endpoints.forEach { e ->
            val raw = if (e.body?.type != null) {
                ObjectMapper()
                        .writeValueAsString(e.body?.type?.createInstance())
            } else {
                ""
            }

            val body = Body(raw = raw)

            val controllerName = if (e.controllerName.isNotEmpty()) {
                e.controllerName
            } else {
                buildFolderName(e.controller)
            }

            val item = getItem(json, controllerName)

            e.patterns.forEach { p ->
                val url = Url(
                        raw = "$path$p",
                        protocol = protocol,
                        port = port)

                val normalizedPath = Regex("\\{(.+)\\}")
                        .replace(p, ":$1")

                url.path.add(normalizedPath)
                url.host.add(host)

                val headers = arrayListOf<Header>()

                e.headers.toSingleValueMap().forEach {
                    headers.add(Header(it.key, it.value))
                }

                e.methods.forEach { m ->
                    val request = Request(
                            method = m,
                            header = headers,
                            body = body,
                            url = url)

                    item.item.add(ItemInfo(
                            name = p,
                            request = request))
                }

                json.item.add(item)
            }
        }

        auth(json)

        return json.toString()
    }

    private fun getItem(json: Json, controllerName: String): Item {
        return json.item
                .firstOrNull { it.name == controllerName }
                ?: Item(controllerName)
    }

    private fun auth(json: Json) {
        json.item.forEach { item ->
            item.item.forEach {
                it.request.header.forEach { header ->
                    if (header.key == AuthorizationFilter.HEADER_NAME) {
                        header.value = "{{$authVarName}}"
                    }
                }

                if (it.name.startsWith("/auth") && it.request.method == "POST") {
                    val script = arrayListOf(
                            "var json = pm.response.json();",
                            "var token = pm.response.headers.get(\"Auth-Token\");",
                            "",
                            "pm.globals.set(\"$authVarName\", \"${AuthorizationFilter.HEADER_VALUE_PREFIX}\" + token);")

                    it.event.add(Event("prerequest"))
                    it.event.add(Event("test", Script(exec = script)))
                }
            }
        }
    }

    private fun buildFolderName(clazz: KClass<*>): String {
        return clazz.simpleName
                ?.substringBefore("Controller")
                ?: clazz.toString()
    }

    companion object {
        val LABEL = "postman"
        val SCHEMA = "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
    }
}
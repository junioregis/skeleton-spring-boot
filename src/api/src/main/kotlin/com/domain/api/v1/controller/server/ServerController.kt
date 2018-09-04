package com.domain.api.v1.controller.server

import com.domain.api.v1.controller.AppController
import com.domain.core.doc.annotation.Doc
import com.domain.core.doc.annotation.DocResponse
import com.domain.core.response.Body
import com.domain.core.response.Response
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/server")
@Doc(name = "server")
class ServerController : AppController() {

    @GetMapping("/ping")
    @Doc(summary = "Ping",
            responses = [
                DocResponse(status = 200, message = "server.pong", description = "Description")])
    fun ping(): ResponseEntity<Body> {
        return Response.Builder()
                .code(200)
                .message("server.pong")
                .build()
    }
}
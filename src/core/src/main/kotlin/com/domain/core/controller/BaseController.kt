package com.domain.core.controller

import com.domain.core.doc.annotation.Doc
import com.domain.core.doc.annotation.DocResponse
import com.domain.core.response.Body
import com.domain.core.response.Response
import com.domain.core.util.Log
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.NoHandlerFoundException
import javax.servlet.http.HttpServletRequest

@Doc(responses = [
    DocResponse(status = 500, description = "error.internal")])
@RestControllerAdvice
abstract class BaseController {

    @Autowired
    protected lateinit var log: Log

    @Autowired
    protected lateinit var request: HttpServletRequest

    @ExceptionHandler(Exception::class)
    fun internalError(e: Exception): ResponseEntity<Body> {
        log.e(e)

        return Response.Builder()
                .code(500)
                .message("error.internal")
                .build()
    }

    @ExceptionHandler(NoHandlerFoundException::class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    fun badRequest(e: Exception): ResponseEntity<Body> {
        log.e(e)

        return Response.Builder()
                .code(404)
                .message("error.routing")
                .build()
    }
}
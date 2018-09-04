package com.domain.api

import com.domain.core.BaseApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Application : BaseApplication()

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
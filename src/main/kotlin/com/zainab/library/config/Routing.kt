package com.zainab.library.config

import com.zainab.library.route.healthRoute
import io.ktor.server.application.Application
import io.ktor.server.routing.routing

fun Application.configureRouting() {
    routing {
        healthRoute()
    }
}

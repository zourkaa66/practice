package com.zainab.library.route

import com.zainab.library.dto.HealthDto
import com.zainab.library.dto.HealthStatus
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.healthRoute() {
    get("/health") {
        val dto = HealthDto(HealthStatus.UP)
        call.respond( HttpStatusCode.OK, dto)
    }
}
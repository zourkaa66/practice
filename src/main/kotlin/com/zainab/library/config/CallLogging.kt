package com.zainab.library.config

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.callid.callIdMdc
import io.ktor.server.plugins.calllogging.CallLogging
import io.ktor.server.plugins.calllogging.processingTimeMillis
import io.ktor.server.request.httpMethod
import io.ktor.server.request.path
import org.slf4j.event.Level

private const val HEALTH_PATH = "/health"

fun Application.configureCallLogging() {
    install(CallLogging) {
        level = Level.INFO
        callIdMdc(FlowId.MDC_KEY)
        filter { call -> call.request.path() != HEALTH_PATH }
        format { call ->
            val method = call.request.httpMethod.value
            val path = call.request.path()
            val status = call.response.status()?.value
            "$method $path $status ${call.processingTimeMillis()}ms"
        }
    }
}
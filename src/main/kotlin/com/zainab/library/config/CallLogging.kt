package com.zainab.library.config

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.callid.callIdMdc
import io.ktor.server.plugins.calllogging.CallLogging
import io.ktor.server.request.path
import org.slf4j.event.Level

private const val HEALTH_PATH = "/health"

fun Application.configureCallLogging() {
    install(CallLogging) {
        level = Level.INFO
        callIdMdc(FlowId.MDC_KEY)
        filter { call -> !call.request.path().startsWith(HEALTH_PATH) }
    }
}

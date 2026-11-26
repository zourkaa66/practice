package com.zainab.library.config

import com.zainab.library.config.FlowId.HEADER_NAME
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.callid.CallId
import java.util.UUID

object FlowId {
    const val HEADER_NAME = "X-Flow-Id"
    const val MDC_KEY = "flow_id"
    const val MAX_LENGTH = 128
    private val allowedChars = Regex("^[A-Za-z0-9_-]+$")

    fun isValid(flowId: String): Boolean = flowId.length <= MAX_LENGTH && flowId.matches(allowedChars)

    fun generate(): String = UUID.randomUUID().toString()
}

fun Application.configureFlowId() {
    install(CallId) {
        retrieveFromHeader(HEADER_NAME)
        replyToHeader(HEADER_NAME)
        generate { FlowId.generate() }
        verify { flowId -> FlowId.isValid(flowId) }
    }
}

package com.zainab.library.config

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.callid.CallId
import java.util.UUID

object FlowId {
    const val HEADER_NAME = "X-Flow-ID"
    const val MDC_KEY = "flow_id"
    private const val MAX_LENGTH = 128
    private val ALLOWED_CHARS = Regex("[A-Za-z0-9_-]+")

    fun isValid(flowId: String): Boolean = flowId.length <= MAX_LENGTH && flowId.matches(ALLOWED_CHARS)

    fun generate(): String = UUID.randomUUID().toString()
}

fun Application.configureFlowId() {
    install(CallId) {
        retrieveFromHeader(FlowId.HEADER_NAME)
        replyToHeader(FlowId.HEADER_NAME)
        generate { FlowId.generate() }
        verify(FlowId::isValid)
    }
}
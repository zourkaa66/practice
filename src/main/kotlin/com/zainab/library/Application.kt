package com.zainab.library

import com.zainab.library.config.ConfigurationProvider
import com.zainab.library.config.configureCallLogging
import com.zainab.library.config.configureFlowId
import com.zainab.library.config.configureRouting
import com.zainab.library.config.configureSerialization
import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.server.application.Application
import io.ktor.server.netty.EngineMain

private val logger = KotlinLogging.logger {}

fun main(args: Array<String>) = EngineMain.main(args)

fun Application.module() {
    val appConfig = ConfigurationProvider.load(environment.config)
    logger.info { "Starting ${appConfig.name}" }

    configureSerialization()
    configureRouting()
    configureFlowId()
    configureCallLogging()
}

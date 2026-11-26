package com.zainab.library.config

import io.ktor.server.config.ApplicationConfig

object ConfigurationProvider {
    private const val APP_NAME_KEY = "app.name"

    fun load(config: ApplicationConfig): AppConfig =
        AppConfig(
            name = config.requireString(APP_NAME_KEY),
        )

    private fun ApplicationConfig.requireString(key: String): String =
        propertyOrNull(key)?.getString()?.takeIf { it.isNotBlank() }
            ?: throw IllegalArgumentException("Missing required config '$key'")
}

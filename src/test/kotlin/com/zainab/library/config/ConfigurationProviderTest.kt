package com.zainab.library.config

import io.ktor.server.config.MapApplicationConfig
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

@DisplayName("ConfigurationProvider")
class ConfigurationProviderTest {
    @Nested
    @DisplayName("when load() is invoked")
    inner class Load {
        @Test
        fun `should return AppConfig when all required keys are set`() {
            val config = MapApplicationConfig("app.name" to "library-service")

            val result = ConfigurationProvider.load(config)

            assertEquals(AppConfig(name = "library-service"), result)
        }

        @Test
        fun `should throw when app name is missing`() {
            val exception =
                assertThrows<IllegalArgumentException> {
                    ConfigurationProvider.load(MapApplicationConfig())
                }

            assertEquals("Missing required config 'app.name'", exception.message)
        }

        @Test
        fun `should throw when app name is blank`() {
            val config = MapApplicationConfig("app.name" to " ")

            assertThrows<IllegalArgumentException> { ConfigurationProvider.load(config) }
        }
    }
}

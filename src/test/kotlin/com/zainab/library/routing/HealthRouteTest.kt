package com.zainab.library.route

import com.zainab.library.module
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.server.testing.testApplication
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

@DisplayName("HealthRoute")
class HealthRouteTest {
    @Nested
    @DisplayName("when GET /health is called")
    inner class GetHealth {
        @Test
        fun `should return 200 and status UP when service is running`() =
            testApplication {
                application { module() }

                val response = client.get("/health")

                assertEquals(HttpStatusCode.OK, response.status)
                assertEquals(ContentType.Application.Json, response.contentType()?.withoutParameters())
                assertEquals("""{"status":"UP"}""", response.bodyAsText())
            }
    }
}
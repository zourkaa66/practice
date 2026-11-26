package com.zainab.library.config

import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.slf4j.MDC
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

private const val ROOT = "/"
private const val VALID_FLOW_ID = "abc-123_XYZ"
private const val INVALID_FLOW_ID = "abc!@#"

@DisplayName("FlowId")
class FlowIdTest {
    private fun ApplicationTestBuilder.setUpApp() {
        application {
            configureFlowId()
            configureCallLogging()
            routing { get(ROOT) { call.respondText(MDC.get(FlowId.MDC_KEY).orEmpty()) } }
        }
    }

    private fun HttpResponse.flowId(): String = assertNotNull(headers[FlowId.HEADER_NAME])

    @Nested
    @DisplayName("when isValid() is invoked")
    inner class IsValid {
        @Test
        fun `should return true when flow id has allowed characters`() {
            assertTrue(FlowId.isValid(VALID_FLOW_ID))
        }

        @Test
        fun `should return false when flow id is empty`() {
            assertFalse(FlowId.isValid(""))
        }

        @Test
        fun `should return false when flow id has invalid characters`() {
            assertFalse(FlowId.isValid(INVALID_FLOW_ID))
        }

        @Test
        fun `should return true when length is exactly max`() {
            assertTrue(FlowId.isValid("a".repeat(FlowId.MAX_LENGTH)))
        }

        @Test
        fun `should return false when length is above max`() {
            assertFalse(FlowId.isValid("a".repeat(FlowId.MAX_LENGTH + 1)))
        }
    }

    @Nested
    @DisplayName("when a request is handled")
    inner class RequestHandling {
        @Test
        fun `should reuse flow id when header is valid`() =
            testApplication {
                setUpApp()
                val response = client.get(ROOT) { header(FlowId.HEADER_NAME, VALID_FLOW_ID) }
                assertEquals(VALID_FLOW_ID, response.flowId())
            }

        @Test
        fun `should generate uuid when header is missing`() =
            testApplication {
                setUpApp()
                val response = client.get(ROOT)
                assertDoesNotThrow { UUID.fromString(response.flowId()) }
            }

        @Test
        fun `should generate new flow id when header has invalid characters`() =
            testApplication {
                setUpApp()
                val response = client.get(ROOT) { header(FlowId.HEADER_NAME, INVALID_FLOW_ID) }
                assertNotEquals(INVALID_FLOW_ID, response.flowId())
            }

        @Test
        fun `should generate new flow id when header is too long`() =
            testApplication {
                setUpApp()
                val sent = "a".repeat(FlowId.MAX_LENGTH + 1)
                val response = client.get(ROOT) { header(FlowId.HEADER_NAME, sent) }
                assertNotEquals(sent, response.flowId())
            }

        @Test
        fun `should put flow id in mdc when request is handled`() =
            testApplication {
                setUpApp()
                val response = client.get(ROOT) { header(FlowId.HEADER_NAME, VALID_FLOW_ID) }
                assertEquals(VALID_FLOW_ID, response.bodyAsText())
            }
    }
}

package demo

import com.ninjasquad.springmockk.MockkBean
import demo.kx.dto.Message
import io.mockk.every
import io.mockk.verify
import demo.kx.services.MessageService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

/**
 * Integration Test mocking web server
 * Mocks library mockk.io
 */
@WebMvcTest
class MockkIntegrationTest(@Autowired val mockMvc: MockMvc) {

    @MockkBean
    private lateinit var service: MessageService

    @Test
    fun `find messages`() {
        every { service.findMessages() } returns listOf(
            Message("1", "First"),
            Message("2", "Second"))

        mockMvc.get("/")
            .andExpect { status { isOk() } }
            .andExpect { content { contentType(MediaType.APPLICATION_JSON) } }
            .andExpect {
                jsonPath("\$.[0].id") { value("1") }
                jsonPath("\$.[0].text") { value("First") }
                jsonPath("\$.[1].id") { value("2") }
                jsonPath("\$.[1].text") { value("Second") }
            }

        verify(exactly = 1) { service.findMessages() }
    }

}


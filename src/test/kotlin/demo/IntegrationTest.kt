package demo

import demo.kx.dto.Message
import demo.kx.extensions.uuid
import kong.unirest.HttpResponse
import kong.unirest.Unirest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.JdbcDatabaseContainer
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName
import kotlin.random.Random

/**
 * Integration Test con TestContainers
 */
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = [DemoApplication::class])
@Testcontainers
class IntegrationTest(
    @Autowired val jdbc: JdbcTemplate,
    @Autowired val mapper: com.fasterxml.jackson.databind.ObjectMapper
) {

    @LocalServerPort
    var port: Int? = null

    @AfterEach
    fun cleanup() {
        jdbc.execute("truncate table messages")
    }

    companion object {

        @Container
        val container = postgres("postgres:13-alpine") {
            withDatabaseName("db")
            withUsername("user")
            withPassword("password")
            withInitScript("sql/schema.sql")
        }

        @JvmStatic
        @DynamicPropertySource
        fun datasourceConfig(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", container::getJdbcUrl)
            registry.add("spring.datasource.password", container::getPassword)
            registry.add("spring.datasource.username", container::getUsername)
        }

    }

    @Test
    fun `container is up and running`() {
        Assertions.assertTrue(container.isRunning)
    }

    @Test
    fun `test hello endpoint`() {
        val response: HttpResponse<String> = Unirest.get("http://localhost:$port/hello").asString()
        assertThat(response.isSuccess)
        assertThat(response.body).contains("Hello")
    }

    @Test
    fun `testing if we can post and retrieve the data`() {
        val id = "${Random.nextInt()}".uuid()
        val message = Message(id, "some message")

        Unirest.post("http://localhost:$port/")
            .header("Content-Type", "application/json")
            .header("Accept", "application/json")
            .body(mapper.writeValueAsString(message))
            .asString()

        val response: HttpResponse<String> = Unirest.get("http://localhost:$port/$id").asString()
        assertThat(response.isSuccess)
        assertThat(response.body).contains(message.id)
        assertThat(response.body).contains(message.text)

        val msg = mapper.readValue(response.body, Message::class.java)
        assertThat(msg.id).isEqualTo(message.id)
        assertThat(msg.text).contains(message.text)
    }

    @Test
    fun `message not found`() {
        val id = "${Random.nextInt()}".uuid()
        val response: HttpResponse<String> = Unirest.get("http://localhost:$port/$id").asString()
        assertThat(response.status).isEqualTo(HttpStatus.NOT_FOUND.value())
    }

}

// funcion de alto nivel para crear una instancia de postgreSQL container
fun postgres(imageName: String, opts: JdbcDatabaseContainer<Nothing>.() -> Unit) =
    PostgreSQLContainer<Nothing>(DockerImageName.parse(imageName)).apply(opts)


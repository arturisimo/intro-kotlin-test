package demo

import demo.kx.dto.Message
import demo.kx.extensions.uuid
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import kotlin.random.Random

/**
 * Integration Test using TestRestTemplate from spring-boot-starter-test
 * Using H2 Database for test
 */
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
    properties = [
        "spring.datasource.url=jdbc:h2:mem:testdb"
    ],
    classes = [DemoApplication::class]
)
class IntegrationTest(@Autowired private val template: TestRestTemplate) {

    @Test
    fun `Test hello endpoint`() {
        val entity: ResponseEntity<String> = template.getForEntity("/hello")
        Assertions.assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        Assertions.assertThat(entity.body).contains("Hello")
    }

    @Test
    fun `Testing if we can post and retrieve data`() {

        val id = Random.nextInt().toString().uuid()
        val message = Message(id,"some message")
        template.postForObject("/", message, Message::class.java)

        val entity: ResponseEntity<String> = template.getForEntity("/$id")
        Assertions.assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        Assertions.assertThat(entity.body).contains(message.id)
        Assertions.assertThat(entity.body).contains(message.text)

        val msg = template.getForObject<Message>("/$id", Message::class.java)!!
        Assertions.assertThat(msg.id).contains(message.id)
        Assertions.assertThat(msg.text).contains(message.text)
    }

    @Test
    fun `Testing not found response`() {

        val id = Random.nextInt().toString().uuid()

        val entity: ResponseEntity<String> = template.getForEntity("/$id")
        Assertions.assertThat(entity.statusCode).isEqualTo(HttpStatus.NOT_FOUND)

    }
}

package demo.kx.config

import demo.kx.dto.Message
import demo.kx.extensions.uuid
import demo.kx.services.MessageService
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.stream.IntStream

@Configuration
class DBLoader {

    @Bean
    fun databaseInitializer(messageService: MessageService) = ApplicationRunner {
        messageService.deleteAll()
        IntStream.range(0, 10).forEach {
            messageService.post(Message("$it".uuid(),"message $it"))
        }
    }

}

package demo.kx.controllers.api

import demo.kx.dto.Message
import demo.kx.services.MessageService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
class MessageResource(val service: MessageService) {

    @GetMapping
    fun index(): List<Message> = service.findMessages()

    @GetMapping("/{id}")
    fun index(@PathVariable id: String): Message =
        service.findMessageById(id)?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Message not found")

    @PostMapping
    fun post(@RequestBody message: Message) {
        service.post(message)
    }

}

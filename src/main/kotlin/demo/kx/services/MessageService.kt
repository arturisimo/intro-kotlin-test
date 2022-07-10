package demo.kx.services

import demo.kx.dto.Message
import demo.kx.extensions.uuid
import org.springframework.http.HttpStatus
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.query
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class MessageService(val db: JdbcTemplate) {

    fun findMessages(): List<Message> = db.query("select * from messages") { rs, _ ->
        Message(rs.getString("id"), rs.getString("text"))
    }

    fun findMessageById(id: String): Message? = db.query("select * from messages where id = ?", id) { rs, _ ->
        Message(rs.getString("id"), rs.getString("text"))
    }.firstOrNull()

    fun post(message: Message){
        db.update("insert into messages values ( ?, ? )", message.id ?: message.text.uuid(),
            message.text)
    }
}

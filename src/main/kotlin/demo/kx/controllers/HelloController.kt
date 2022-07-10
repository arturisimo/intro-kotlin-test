package demo.kx.controllers

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HelloController {

    @GetMapping("/hello")
    fun index(model: Model): String {
        model["message"] = "Hello"
        return "greeting"
    }
}

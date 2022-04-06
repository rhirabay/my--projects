package rhirabay.spring.thymeleaf

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute

@Controller
class HelloController {
    companion object {
        val log: Logger = LoggerFactory.getLogger(this::class.java.enclosingClass)
    }

    @GetMapping("/hello")
    suspend fun hello(model: Model) : String {
        log.info("hello")
        //model.addAttribute("message", "Hello, Thymeleaf!")
        return "index";
    }

    @ModelAttribute("message1")
    fun messageAttr(model: Model) : String {
        log.info("message model attribute")
        return "Hello, Thymeleaf."
    }
}

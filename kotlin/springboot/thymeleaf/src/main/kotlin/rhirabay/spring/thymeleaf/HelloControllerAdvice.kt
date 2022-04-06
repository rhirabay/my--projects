package rhirabay.spring.thymeleaf

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ModelAttribute

@ControllerAdvice
class HelloControllerAdvice {
    companion object {
        val log: Logger = LoggerFactory.getLogger(this::class.java.enclosingClass)
    }

    @ModelAttribute
    fun messageAttr(model: Model) {
        log.info("message2. ${model.getAttribute("message2")}")
        //Thread.sleep(3_000)
        model.addAttribute("message2", "Hello, Thymeleaf.")
//        return "Hello, Thymeleaf."
    }
}
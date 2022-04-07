package rhirabay.kotlin.coroutines.controller

import org.apache.logging.log4j.LogManager
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class SampleController(private val greetService: GreetService) {
    @GetMapping("/hello")
    suspend fun hello(@RequestParam(required = false) name: String?): String {
        return greetService.greeting(name = name?: "anonymous")
    }
}

@Component
class GreetService {
    suspend fun greeting(name: String): String {
        Thread.sleep(2_000)
        return "Hello, ${name}."
    }
}

@Aspect
@Component
class GreetServiceAdvice() {
    @Around("execution(* *..*GreetService.*(..))")
    fun invoke(joinPoint: ProceedingJoinPoint): Any {
        val start = System.currentTimeMillis()
        try {
            val result = joinPoint.proceed()
            log.info("result class: ${result::class}")

            return result
        } catch (th: Throwable) {
            throw th
        } finally {
            val end = System.currentTimeMillis()
            log.info("${joinPoint.signature.name} in ${end - start} ms")
        }
    }

    companion object{
        private val log = LogManager.getLogger(GreetServiceAdvice::class.java)
    }
}
package rhirabay.grpc.yidongnan.grpc

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import rhirabay.grpc.yidongnan.grpc.client.GreetClient

@RestController
class Controller(private val greetClient: GreetClient) {
    @GetMapping("/greeting")
    fun greeting(@RequestParam(defaultValue = "anonymous", required = false) name: String): String {
        return greetClient.greeting(name)
    }
}
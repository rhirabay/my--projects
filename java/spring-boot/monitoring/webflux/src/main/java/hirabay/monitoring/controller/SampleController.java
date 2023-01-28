package hirabay.monitoring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.time.Duration;

@RestController
@RequiredArgsConstructor
public class SampleController {
    private final WebClient myWebClient;

    @GetMapping("/sample")
    public String sample() {
        return "ok";
    }

    @GetMapping("/server")
    public Mono server() {
        return Mono.just("ok")
                .delayElement(Duration.ofSeconds(1));
    }

    @GetMapping("/client")
    public Mono<String> client() {
        return myWebClient.get()
                .uri("http://localhost:8080/server")
                .retrieve()
                .bodyToMono(String.class);
    }

}

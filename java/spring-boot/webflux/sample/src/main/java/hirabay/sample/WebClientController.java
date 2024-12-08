package hirabay.sample;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class WebClientController {
    private final WebClient.Builder webClientBuilder;

    @GetMapping("/webclient/ng1")
    public Mono<String> ng1() {
        return webClientBuilder
                .baseUrl("http://localhost:8080")
                .build()
                .get()
                .uri("/api/sample?uuid=" + UUID.randomUUID().toString())
                .retrieve()
                .bodyToMono(String.class);
    }

    @GetMapping("/webclient/ng2")
    public Mono<String> ng2() {
        return webClientBuilder
                .baseUrl("http://localhost:8080")
                .build()
                .get()
                .uri(
                        uriBuilder ->
                                uriBuilder
                                        .path("/api/sample")
                                        .queryParam("uuid", UUID.randomUUID().toString())
                                        .build())
                .retrieve()
                .bodyToMono(String.class);
    }

    @GetMapping("/webclient/ok")
    public Mono<String> ok() {
        return webClientBuilder
                .baseUrl("http://localhost:8080")
                .build()
                .get()
                .uri("/api/sample?uuid={uuid}", UUID.randomUUID().toString())
                .retrieve()
                .bodyToMono(String.class);
    }

    @GetMapping("/webclient/ok2")
    public Mono<String> ok2() {
        var uri =
                UriComponentsBuilder.fromPath("/api/sample")
                        .queryParam("uuid", "{uuid}")
                        .build()
                        .toUriString();

        return webClientBuilder
                .baseUrl("http://localhost:8080")
                .build()
                .get()
                .uri(uri, UUID.randomUUID().toString())
                .retrieve()
                .bodyToMono(String.class);
    }
}

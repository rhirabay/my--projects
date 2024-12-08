package hirabay.webflux.infrastructure;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class SampleApiClient {
    private final WebClient webClient;

    public Mono<String> sample() {
        return webClient
                .get()
                .uri("/sample")
                .retrieve()
                .bodyToMono(SampleResponse.class)
                .map(res -> res.getMessage());
    }

    @Data
    public static class SampleResponse {
        private String message;
    }
}

package hirabay.webflux;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import hirabay.webflux.infrastructure.SampleApiClient;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@WireMockTest
public class ErrorResponseMappingTest {
    private ObjectMapper objectMapper = new ObjectMapper();

    private WebClient webClient;
    private WebClient webClientWithErrorHandler;

    @BeforeEach
    void beforeEach(WireMockRuntimeInfo wmRuntimeInfo) {
        int port = wmRuntimeInfo.getHttpPort();
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:" + port)
                .build();

        this.webClientWithErrorHandler = WebClient.builder()
                .baseUrl("http://localhost:" + port)
                .filter(ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
                    if (clientResponse.statusCode().is4xxClientError() ||
                            clientResponse.statusCode().is5xxServerError()) {
                        // 4xxや5xxのステータスコードの場合、カスタムの例外をスロー
                        return clientResponse.bodyToMono(SampleResponse.class)
                                .flatMap(errorBody -> Mono.error(new SampleException(errorBody)));
                    }
                    // エラーがない場合はそのままレスポンスを返す
                    return Mono.just(clientResponse);
                }))
                .build();
    }

    @Test
    @SneakyThrows
    void test() {

        // Mockサーバのレスポンスを設定する
        var responseBody = new SampleResponse();
        responseBody.setMessage("Hello, world.");
        WireMock.stubFor(WireMock.get("/sample")
                .willReturn(WireMock.serverError()
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(responseBody))));

        // リクエスト送信
        var actual = this.webClient.get()
                .uri("/sample")
                .retrieve()
                .bodyToMono(Void.class)
                .onErrorResume(ex -> {
                    if (ex instanceof WebClientResponseException wre) {
                        var errorResponseBody = wre.getResponseBodyAs(SampleResponse.class);
                        return Mono.error(new SampleException(errorResponseBody));
                    }

                    return Mono.empty();
                });

        log.info("actual: {}", actual);

        StepVerifier.create(actual)
                .expectErrorSatisfies(ex -> {
                    var sampleException = (SampleException)ex;
                    assertThat(sampleException.getErrorResponseBody()).isEqualTo(responseBody);
                })
                .verify();
//                .expectNext(responseBody)
//                .verifyComplete();

        actual = webClientWithErrorHandler.get()
                .uri("/sample")
                .retrieve()
                .bodyToMono(Void.class);

        StepVerifier.create(actual)
                .expectErrorSatisfies(ex -> {
                    var sampleException = (SampleException)ex;
                    assertThat(sampleException.getErrorResponseBody()).isEqualTo(responseBody);
                })
                .verify();
    }

    @Data
    public static class SampleResponse {
        private String message;
    }

    @Getter
    public static class SampleException extends RuntimeException {
        private final SampleResponse errorResponseBody;

        public SampleException(SampleResponse errorResponseBody) {
            super();
            this.errorResponseBody = errorResponseBody;
        }
    }
}

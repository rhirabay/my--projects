package hirabay.webflux;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import hirabay.webflux.infrastructure.SampleApiClient;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

@WireMockTest
public class SampleApiClientTest {
    private ObjectMapper objectMapper = new ObjectMapper();
    private SampleApiClient sampleApiClient;

    @BeforeEach
    void beforeEach(WireMockRuntimeInfo wmRuntimeInfo) {
        var webClient = WebClient.builder().baseUrl(wmRuntimeInfo.getHttpBaseUrl()).build();

        sampleApiClient = new SampleApiClient(webClient);
    }

    @Test
    @SneakyThrows
    void test() {
        // Mockサーバのレスポンスを設定する
        var responseBody = new SampleApiClient.SampleResponse();
        responseBody.setMessage("Hello, world.");
        WireMock.stubFor(
                WireMock.get("/sample")
                        .willReturn(
                                WireMock.aResponse()
                                        .withBody(objectMapper.writeValueAsString(responseBody))
                                        .withHeader("Content-Type", "application/json")));

        // リクエスト送信
        var actual = sampleApiClient.sample();

        StepVerifier.create(actual).expectNext("Hello, world.").verifyComplete();
    }
}

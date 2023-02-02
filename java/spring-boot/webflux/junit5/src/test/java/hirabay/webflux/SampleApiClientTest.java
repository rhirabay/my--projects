package hirabay.webflux;

import com.fasterxml.jackson.databind.ObjectMapper;
import hirabay.webflux.infrastructure.SampleApiClient;
import lombok.SneakyThrows;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.io.IOException;


public class SampleApiClientTest {
    private static MockWebServer mockServer;
    private ObjectMapper objectMapper = new ObjectMapper();

    private SampleApiClient sampleApiClient;

    @BeforeEach
    void beforeEach() {
        var webClient = WebClient.builder()
                .baseUrl("http://localhost:" + mockServer.getPort())
                .build();

        sampleApiClient = new SampleApiClient(webClient);
    }

    @BeforeAll
    static void startMock() throws IOException {
        mockServer = new MockWebServer();
        mockServer.start();
    }

    @AfterAll
    static void shutdownMock() throws IOException {
        mockServer.shutdown();
    }

    @Test
    @SneakyThrows
    void test() {
        // Mockサーバのレスポンスを設定する
        var responseBody = new SampleApiClient.SampleResponse();
        responseBody.setMessage("Hello, world.");
        var mockedResponse = new MockResponse()
                .setBody(objectMapper.writeValueAsString(responseBody))
                .addHeader("Content-Type", "application/json");
        mockServer.enqueue(mockedResponse);

        // リクエスト送信
        var actual = sampleApiClient.sample();

        StepVerifier.create(actual)
                .expectNext("Hello, world.")
                .verifyComplete();
    }
}

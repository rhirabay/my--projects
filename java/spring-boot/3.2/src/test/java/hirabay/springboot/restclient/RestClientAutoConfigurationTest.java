package hirabay.springboot.restclient;

import com.github.tomakehurst.wiremock.client.WireMock;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.actuate.observability.AutoConfigureObservability;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@Slf4j
@SpringBootTest(properties = {
        "restclient.base-url=http://localhost:${wiremock.server.port}"
})
@AutoConfigureWireMock(port = 0)
@AutoConfigureObservability
class RestClientAutoConfigurationTest {
    @Autowired
    RestClient customRestClient;

    @Autowired
    RestClient defaultRestClient;

    // prometheusのregisterをDI
    @Autowired
    PrometheusMeterRegistry prometheusMeterRegistry;

    @Test
    void test_customRestClient() {
        // Stubbing WireMock
        stubFor(get(urlPathMatching("/restClientTest")).willReturn(aResponse()
                //.withFixedDelay(10_000)
                .withHeader("Content-Type", "text/plain").withBody("Hello World!")));

        var actual = customRestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/restClientTest")
                        .queryParam("key", "value")
                        .build())
                //.uri("/restClientTest?key={key}", Map.of("key", "value"))
                .retrieve()
                .body(String.class);
        log.info("actual: {}", actual);
        // prometheusのメトリクスをすべて標準出力
        var metrics = prometheusMeterRegistry.scrape();
        log.info("metrics: {}", metrics);
    }

    @Test
    void test_defaultRestClient() {
        // Stubbing WireMock
        stubFor(get(urlEqualTo("/restClientTest")).willReturn(aResponse()
                .withHeader("Content-Type", "text/plain").withBody("Hello World!")));

        var actual = defaultRestClient.get()
                .uri("/restClientTest")
                .retrieve()
                .body(String.class);
        log.info("actual: {}", actual);
    }

    @Test
    void test() {
        var restClientBuilder = RestClient.builder();

        MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restClientBuilder).build();
        mockServer.expect(requestTo("/greeting"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess().body("Hello World!"));

        var restClient = restClientBuilder.build();
        var sampleClient = new SampleClient(restClient);
        var actual = sampleClient.greeting();
        var expected = "Hello World!";
        assertThat(actual).isEqualTo(expected);

        mockServer.verify();
    }

    // HTTPリクエストを送るクライアントクラス
    @RequiredArgsConstructor
    public static class SampleClient {
        private final RestClient restClient;

        public String greeting() {
            return restClient.get()
                    .uri("/greeting")
                    .retrieve()
                    .body(String.class);
        }
    }
}
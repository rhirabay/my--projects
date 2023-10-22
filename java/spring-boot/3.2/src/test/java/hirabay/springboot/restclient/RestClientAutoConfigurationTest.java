package hirabay.springboot.restclient;

import com.github.tomakehurst.wiremock.client.WireMock;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.actuate.observability.AutoConfigureObservability;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.web.client.RestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

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
        stubFor(get(urlEqualTo("/restClientTest")).willReturn(aResponse()
                //.withFixedDelay(10_000)
                .withHeader("Content-Type", "text/plain").withBody("Hello World!")));

        var actual = customRestClient.get()
                .uri("/restClientTest")
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
}
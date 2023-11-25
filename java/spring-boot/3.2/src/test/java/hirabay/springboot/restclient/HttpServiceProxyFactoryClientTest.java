package hirabay.springboot.restclient;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest(properties = {
        "restclient.base-url=http://localhost:${wiremock.server.port}"
})
@AutoConfigureWireMock(port = 0)
class HttpServiceProxyFactoryClientTest {
    @Autowired
    HttpServiceProxyFactoryClient httpServiceProxyFactoryClient;

    @Test
    void test() {
        stubFor(get(urlPathMatching("/restClient/proxyFactory/greeting")).willReturn(aResponse()
                .withHeader("Content-Type", "text/plain")
                .withBody("Hello World!")));

        var actual = httpServiceProxyFactoryClient.greeting("hirabay");
        var expected = "Hello World!";
        assertThat(actual).isEqualTo(expected);
    }
}
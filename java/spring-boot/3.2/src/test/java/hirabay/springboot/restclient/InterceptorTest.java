package hirabay.springboot.restclient;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

class SampleClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution exec) throws IOException {
        request.getHeaders().set("sample", "sample value");
        return exec.execute(request, body);
    }
}

class InterceptorTest {
    @Test
    void test() {
var restClientBuilder = RestClient.builder()
        // 単純な追加の場合
        .requestInterceptor(new SampleClientHttpRequestInterceptor())
        // 既存のinterceptorをクリアしたり、順序を調整したい場合
        .requestInterceptors(interceptors -> {
            interceptors.clear();
            interceptors.add(new SampleClientHttpRequestInterceptor());
        });

        MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restClientBuilder).build();
        mockServer.expect(requestTo("/greeting"))
                .andExpect(method(HttpMethod.GET))
                .andExpect(header("sample", "sample value"))
                .andRespond(withSuccess().body("Hello World!"));

        var restClient = restClientBuilder.build();
        var actual = restClient.get()
                .uri("/greeting")
                .retrieve()
                .body(String.class);

        var expected = "Hello World!";
        assertThat(actual).isEqualTo(expected);

        mockServer.verify();
    }
}

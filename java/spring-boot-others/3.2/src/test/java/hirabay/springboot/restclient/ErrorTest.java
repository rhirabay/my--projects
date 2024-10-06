package hirabay.springboot.restclient;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Duration;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withBadRequest;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@Slf4j
class ErrorTest {
    @Test
    void test_exception_handling() {
        var restClientBuilder = RestClient.builder();

        MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restClientBuilder).build();
        mockServer.expect(requestTo("/greeting"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withServerError());

        var restClient = restClientBuilder.build();
        try {
            restClient.get()
                    .uri("/greeting")
                    .retrieve()
                    .body(String.class);
        } catch (HttpClientErrorException httpClientErrorException) {
            // 4xx系のHTTPステータスが帰ってきた場合の処理
            log.info("4xx error");
        } catch (HttpServerErrorException httpServerErrorException) {
            // 5xx系のHTTPステータスが帰ってきた場合の処理
            log.info("5xx error");
        } catch (HttpStatusCodeException httpStatusCodeException) {
            // HTTPステータスがあるエラーが帰ってきた場合の処理
            // HttpClientErrorException + HttpServerErrorExceptionのイメージ
            var statusCode = httpStatusCodeException.getStatusCode(); // ステータスコードの取得
            var errorBoyd = httpStatusCodeException.getResponseBodyAs(String.class); // レスポンスBodyの取得
            log.info("status: {}, body: {}", statusCode, errorBoyd);
        } catch (ResourceAccessException resourceAccessException) {
            // 通信エラー系の処理（タイムアウト、証明書エラー等）
            log.info("io error");
        }

        mockServer.verify();
    }

    @Test
    void test_response_handler() {
        var restClientBuilder = RestClient.builder();

        MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restClientBuilder).build();
        mockServer.expect(requestTo("/greeting"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withServerError());

        var restClient = restClientBuilder.build();
        restClient.get()
                .uri("/greeting")
                .retrieve()
                .onStatus(HttpStatusCode::isError, (request, response) -> {
                    // エラー時の処理
                    // 例）一般的な例外や独自の業務例外をスローする
                    throw new RuntimeException();
                })
                .body(String.class);

        restClient.get()
                .uri("/greeting")
                .retrieve()
                .onStatus(new DefaultResponseErrorHandler() {
                    @Override
                    public boolean hasError(HttpStatusCode statusCode) {
                        return false;
                    }
                })
                .body(String.class);
    }

    @Test
    void test_5xx() {
        var restClientBuilder = RestClient.builder();

        MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restClientBuilder).build();
        mockServer.expect(requestTo("/greeting"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withServerError());

        var restClient = restClientBuilder.build();
        var actual = restClient.get()
                .uri("/greeting")
                .retrieve()
                .body(String.class);

        mockServer.verify();
    }

    @Test
    void test_4xx() {

        var restClientBuilder = RestClient.builder();

        MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restClientBuilder).build();
        mockServer.expect(requestTo("/greeting"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withBadRequest());

        var restClient = restClientBuilder.build();
        var actual = restClient.get()
                .uri("/greeting")
                .retrieve()
                .body(String.class);

        mockServer.verify();
    }

    @Nested
    @WireMockTest
    class WireMockedTest {
        @Test
        void test_timeout(WireMockRuntimeInfo wmRuntimeInfo) {
            stubFor(get("/greeting").willReturn(ok().withFixedDelay(500)));

            var requestFactory = new SimpleClientHttpRequestFactory();
            requestFactory.setReadTimeout(100);

            var restClient = RestClient.builder()
                    .baseUrl("http://localhost:" + wmRuntimeInfo.getHttpPort())
                    .requestFactory(requestFactory)
                    .build();

            var actual = restClient.get()
                    .uri("/greeting")
                    .retrieve()
                    .body(String.class);

        }
    }
}

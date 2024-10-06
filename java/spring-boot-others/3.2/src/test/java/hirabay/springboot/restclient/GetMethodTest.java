package hirabay.springboot.restclient;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.queryParam;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RequiredArgsConstructor
class GetMethodClient {
    private final RestClient restClient;

    public String getSample() {
        return restClient.get() // HTTPメソッドを指定
                .uri("/greeting?name={name}", Map.of("name", "hirabay")) // パスとクエリパラメータを指定
                .retrieve() // HTTPリクエストを実行
                .body(String.class); // レスポンスを受け取る型を指定
    }

    public String getEntitySample() {
        var responseEntity = restClient.get() // HTTPメソッドを指定
                .uri("/greeting?name={name}", Map.of("name", "hirabay")) // パスとクエリパラメータを指定
                .retrieve() // HTTPリクエストを実行
                .toEntity(String.class); // レスポンスを受け取る型を指定

        HttpStatusCode httpStatusCode = responseEntity.getStatusCode();
        // ステータスコードを利用した処理
        HttpHeaders httpHeaders = responseEntity.getHeaders();
        // レスポンスヘッダを利用した処理

        return responseEntity.getBody();
    }
}

class GetMethodTest {
    @Test
    void test() {
        var restClientBuilder = RestClient.builder();

        MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restClientBuilder).build();
        var expectedUri = UriComponentsBuilder.fromUriString("/greeting")
                .queryParam("name", "hirabay")
                .build()
                .toUriString();
        mockServer.expect(requestTo(expectedUri))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess().body("Hello World!"));

        var restClient = restClientBuilder.build();
        var getMethodClient = new GetMethodClient(restClient);
        var actual = getMethodClient.getSample();
        var expected = "Hello World!";
        assertThat(actual).isEqualTo(expected);

        mockServer.verify();
    }
}

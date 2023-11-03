package hirabay.springboot.restclient;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RequiredArgsConstructor
class PostMethodClient {
    private final RestClient restClient;

    public String postSample() {
        var person = new Person("hirabay");

        return restClient.post() // HTTPメソッドを指定
                .uri("/greeting") // パスを指定
                .body(person)
                .retrieve() // HTTPリクエストを実行
                .body(String.class); // レスポンスを受け取る型を指定
    }

    public record Person (String name) {}

}

class PostMethodTest {
    @Test
    void test() {
        var restClientBuilder = RestClient.builder();

        MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restClientBuilder).build();
        mockServer.expect(requestTo("/greeting"))
                .andExpect(method(HttpMethod.POST))
                // jsonをまるまる検証する場合
                .andExpect(content().json("""
                        {
                            "name": "hirabay"
                        }
                        """))
                // jsonの特定のフィールドを検証する場合
                .andExpect(jsonPath("$.name").value("hirabay"))
                .andRespond(withSuccess().body("Hello World!"));

        var restClient = restClientBuilder.build();
        var postMethodClient = new PostMethodClient(restClient);
        var actual = postMethodClient.postSample();
        var expected = "Hello World!";
        assertThat(actual).isEqualTo(expected);

        mockServer.verify();
    }
}

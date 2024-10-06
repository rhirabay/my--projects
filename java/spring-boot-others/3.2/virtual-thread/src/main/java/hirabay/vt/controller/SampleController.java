package hirabay.vt.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class SampleController {

    public SampleController() {
        // PoolingHttpClientConnectionManagerを使うことでコネクションがプールされて
        // リクエストごとにコネクションを確立する必要がなくなる
        var connectionManager = PoolingHttpClientConnectionManagerBuilder.create()
                // 全ルート合算の最大接続数
                .setMaxConnTotal(100)
                .setMaxConnPerRoute(100)
                .build();

        var httpClient = HttpClientBuilder.create()
                .setConnectionManager(connectionManager)
                .build();

        var requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        this.restClient = RestClient.builder()
                .baseUrl("http://localhost:8080")
                .requestFactory(requestFactory)
                .build();
    }
    private final RestClient restClient;

    @GetMapping("/client")
    public String client(@RequestParam(required = false, defaultValue = "0") int sleep) {
        var uri = UriComponentsBuilder.fromUriString("/server")
                .queryParam("sleep", sleep)
                .build()
                .toUriString();
        return restClient.get()
                .uri(uri)
                .retrieve()
                .body(String.class);
    }

    @GetMapping("/server")
    public String server(@RequestParam(required = false, defaultValue = "0") int sleep) throws Exception {
        log.info("sleep: {}, virtual: {}", sleep, Thread.currentThread().isVirtual());
        var parentThread = Thread.currentThread().getThreadGroup().getParent();
        log.info("parentThread: {}", parentThread);
        TimeUnit.SECONDS.sleep(sleep);

        return "test";
    }
}

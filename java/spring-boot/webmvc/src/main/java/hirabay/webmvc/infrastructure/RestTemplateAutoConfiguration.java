package hirabay.webmvc.infrastructure;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.core5.http.io.SocketConfig;
import org.apache.hc.core5.util.TimeValue;
import org.springframework.boot.actuate.metrics.web.client.ObservationRestTemplateCustomizer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateAutoConfiguration {
    @Bean
    RestTemplate restTemplate() {
        var socketConfig =
                SocketConfig.custom()
                        // read timeout（例：1000ミリ秒）
                        .setSoTimeout(1_000, TimeUnit.MILLISECONDS)
                        .setSoKeepAlive(false)
                        .build();
        var connectionConfig =
                ConnectionConfig.custom()
                        // keep alive timeout（例: 59秒）
                        .setTimeToLive(TimeValue.ofSeconds(59))
                        .build();
        var connectionManager =
                PoolingHttpClientConnectionManagerBuilder.create()
                        .setDefaultConnectionConfig(connectionConfig)
                        // 全ルート合算の最大接続数
                        .setMaxConnTotal(100)
                        // ルート（基本的にはドメイン）ごとの最大接続数
                        // !!! デフォルトが「5」で高負荷には耐えられない設定値なので注意 !!!
                        .setMaxConnPerRoute(100)
                        .setDefaultSocketConfig(socketConfig)
                        .build();

        var httpClient = HttpClientBuilder.create().setConnectionManager(connectionManager).build();

        var requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);

        return new RestTemplateBuilder()
                .requestFactory(() -> requestFactory)
                .rootUri("http://localhost:8080/rest-template-test")
                .connectTimeout(Duration.ofMillis(500))
                .build();
    }

    @Bean
    RestTemplate restTemplateWithMetrics(ObservationRestTemplateCustomizer observationCustomizer) {
        return new RestTemplateBuilder()
                .rootUri("http://localhost:8080/rest-template-test")
                .additionalCustomizers(observationCustomizer)
                .build();
    }
}

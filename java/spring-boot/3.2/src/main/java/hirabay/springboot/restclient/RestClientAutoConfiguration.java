package hirabay.springboot.restclient;

import io.micrometer.observation.ObservationRegistry;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.core5.http.io.SocketConfig;
import org.apache.hc.core5.util.TimeValue;
import org.apache.hc.core5.util.Timeout;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class RestClientAutoConfiguration {
    @Value("${restclient.base-url}")
    private String baseUrl;

    @Bean
    RestClient customRestClient(
            ObservationRegistry observationRegistry // メトリクス取得のためのBean
    ) {
        var connectionConfig = ConnectionConfig.custom()
                .setTimeToLive(TimeValue.of(59, TimeUnit.SECONDS))
                .setConnectTimeout(Timeout.of(1, TimeUnit.SECONDS))
                .setSocketTimeout(Timeout.of(5, TimeUnit.SECONDS))
                .build();

        var connectionManager = PoolingHttpClientConnectionManagerBuilder.create()
                .setDefaultConnectionConfig(connectionConfig)
                // 全ルート合算の最大接続数
                .setMaxConnTotal(100)
                // ルート（基本的にはドメイン）ごとの最大接続数
                // !!! デフォルトが「5」で高負荷には耐えられない設定値なので注意 !!!
                .setMaxConnPerRoute(100)
                .build();

        var httpClient = HttpClientBuilder.create()
                .setConnectionManager(connectionManager)
                .build();

        var requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        requestFactory.setConnectTimeout(Duration.ofSeconds(1));
        return RestClient.builder()
                .baseUrl(baseUrl)
                .observationRegistry(observationRegistry) // メトリクス取得設定
                .requestFactory(requestFactory)
                .defaultStatusHandler(new DefaultResponseErrorHandler())
                .build();
    }

    @Bean
    RestClient defaultRestClient(RestClient.Builder builder) {
        return builder
                .baseUrl(baseUrl)
                .build();
    }
}

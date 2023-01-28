package hirabay.monitoring.client;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.httpcomponents.PoolingHttpClientConnectionManagerMetricsBinder;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.springframework.boot.actuate.metrics.web.client.ObservationRestTemplateCustomizer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateAutoConfiguration {
    @Bean
    public PoolingHttpClientConnectionManager connectionManager() {
        return PoolingHttpClientConnectionManagerBuilder.create()
                .setMaxConnTotal(100)    // 最大コネクション数　※デフォルト: 25
                .setMaxConnPerRoute(50)  // ドメインごとの最大コネクション数　※デフォルト: 5
                .build();
    }
//
//    @Bean
//    public PoolingHttpClientConnectionManagerMetricsBinder connectionManagerMetricsBinder(
//            MeterRegistry meterRegistry,
//            PoolingHttpClientConnectionManager connectionManager
//    ) {
//        return new PoolingHttpClientConnectionManagerMetricsBinder(connectionManager, "http-client")
//                .bindTo(meterRegistry);
//    }

    @Bean
    public RestTemplate myRestTemplate(
            RestTemplateBuilder restTemplateBuilder,
            PoolingHttpClientConnectionManager connectionManager,
            ObservationRestTemplateCustomizer observationRestTemplateCustomizer
    ) {
        var httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .build();

        var requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);

        return new RestTemplateBuilder()
                // HTTP通信のメトリクス取得のためのCustomizer
                // （Springが提供しているRestTemplateBuilder beanを使う場合は不要）
                .customizers(observationRestTemplateCustomizer)
                .requestFactory(() -> requestFactory)
                .build();
    }
}

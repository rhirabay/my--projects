package com.example.demo.resttemplate;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.httpcomponents.PoolingHttpClientConnectionManagerMetricsBinder;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class RestTemplateSampleAutoConfiguration {
    @Bean
    public PoolingHttpClientConnectionManager connectionManager() {
        return new PoolingHttpClientConnectionManager();
    }

    @Bean
    public PoolingHttpClientConnectionManagerMetricsBinder connectionManagerMetricsBinder(
            MeterRegistry meterRegistry,
            PoolingHttpClientConnectionManager connectionManager
    ) {
        var binder = new PoolingHttpClientConnectionManagerMetricsBinder(connectionManager, "http-client");
        binder.bindTo(meterRegistry);

        return binder;
    }

    @Bean
    public RestTemplate myRestTemplate(PoolingHttpClientConnectionManager connectionManager) {
        HttpClient httpClient = HttpClientBuilder.create()
                .setConnectionManager(connectionManager)
                .build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);

        return new RestTemplateBuilder()
                .requestFactory(() -> requestFactory)
                .build();
    }
}

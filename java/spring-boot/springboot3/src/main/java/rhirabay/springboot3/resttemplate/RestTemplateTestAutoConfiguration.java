package rhirabay.springboot3.resttemplate;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestTemplateTestAutoConfiguration {
    @Bean
    public RestTemplate restTemplate1() {
        RestTemplate restTemplate = new RestTemplateBuilder()
                .rootUri("http://localhost:8080")
                .build();
        return restTemplate;
    }

    @Bean
    public RestTemplate restTemplate2() {
        PoolingHttpClientConnectionManager connectionManager = PoolingHttpClientConnectionManagerBuilder.create()
                .setConnPoolPolicy()
                .build();
        connectionManager.setDefaultMaxPerRoute(10);
        connectionManager.setMaxTotal(10);
        connectionManager.connection

        HttpClient httpClient = HttpClientBuilder.create()
                .setConnectionManager()
                .build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        return new RestTemplateBuilder()
                .requestFactory(() -> requestFactory)
                .build();
    }
}

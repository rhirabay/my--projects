package hirabay.springboot3;

import lombok.SneakyThrows;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactoryBuilder;
import org.apache.hc.core5.http.io.SocketConfig;
import org.apache.hc.core5.http.ssl.TLS;
import org.apache.hc.core5.util.TimeValue;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.util.concurrent.TimeUnit;

public class RestTemplateSampleAutoConfiguration {
    @Bean
    @SneakyThrows
    public RestTemplate myRestTemplate() {
        // httpclient4
        // SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(SSLContext.getInstance("TLSv1.2"));

        // httpclient5
        SSLConnectionSocketFactory socketFactory = SSLConnectionSocketFactoryBuilder.create()
                .setTlsVersions(TLS.V_1_2)
                .build();
        var socketConfig = SocketConfig.custom().setSoTimeout(1_000, TimeUnit.SECONDS);

        // httpclient4
        // PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        // connectionManager.setMaxTotal(10);

        // httpclient5
        PoolingHttpClientConnectionManager connectionManager = PoolingHttpClientConnectionManagerBuilder.create()
                .setMaxConnTotal(10)
                .setSSLSocketFactory(socketFactory)
                .setDefaultSocketConfig(null)
                .build();


        HttpClient httpClient = HttpClientBuilder.create()
                .setConnectionManager(connectionManager)
                .build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        requestFactory.setConnectTimeout(10);
        requestFactory.setReadTimeout(1_000);

        return new RestTemplateBuilder()
                .requestFactory(() -> requestFactory)
                .build();
    }
}

package hirabay.monitoring.client;

import io.netty.channel.ChannelOption;
import org.springframework.boot.actuate.metrics.web.reactive.client.ObservationWebClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

@Configuration
public class WebClientAutoConfiguration {
    @Bean
    public WebClient myWebClient(ObservationWebClientCustomizer observationWebClientCustomizer) {
        var connectionProvider = ConnectionProvider.builder("sample")
                .maxConnections(100) // コネクションプール数
                .metrics(true) // コネクション数のメトリクスを有効化
                .build();

        var httpClient = HttpClient.create(connectionProvider);

        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .apply(observationWebClientCustomizer::customize)  // http clientのメトリクスを収集
                .build();
    }
}

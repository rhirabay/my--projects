package rhirabay.designpattern.factorymethod.applied;

import io.netty.channel.ChannelOption;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(ApplicationProperties.class)
public class WebClientFactory {
    private final ApplicationProperties webClientProperties;

    public WebClient create(String name) {
        var property = webClientProperties.getWebClient().get(name);

        var httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, (int)property.getConnectTimeout().toMillis())
                .responseTimeout(property.getReadTimeout());

        return WebClient.builder()
                .baseUrl(property.getBaseurl())
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}

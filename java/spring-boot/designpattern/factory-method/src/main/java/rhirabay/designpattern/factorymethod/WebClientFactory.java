package rhirabay.designpattern.factorymethod;

import io.netty.channel.ChannelOption;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

//@Component
public class WebClientFactory {
    public WebClient create(
            String baseurl,
            Duration readTimeout,
            Duration connectTimeout
    ) {
        var httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, (int)connectTimeout.toMillis())
                .responseTimeout(readTimeout);

        return WebClient.builder()
                .baseUrl(baseurl)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}

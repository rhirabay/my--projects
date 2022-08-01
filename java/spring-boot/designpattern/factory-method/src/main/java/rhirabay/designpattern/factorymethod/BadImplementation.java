package rhirabay.designpattern.factorymethod;

import io.netty.channel.ChannelOption;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

//@Configuration
public class BadImplementation {
    @Bean
    public WebClient webClientForA() {
        var httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1_000)
                .responseTimeout(Duration.ofMillis(5000));

        return WebClient.builder()
                .baseUrl("baseurlA")
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    @Bean
    public WebClient webClientForB() {
        var httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1_000)
                .responseTimeout(Duration.ofMillis(5000));

        return WebClient.builder()
                .baseUrl("baseurlB")
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}

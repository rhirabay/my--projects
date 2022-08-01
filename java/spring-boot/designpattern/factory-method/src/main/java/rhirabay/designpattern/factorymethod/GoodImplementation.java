package rhirabay.designpattern.factorymethod;

import io.netty.channel.ChannelOption;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

//@Configuration
@RequiredArgsConstructor
public class GoodImplementation {
    private final WebClientFactory webClientFactory;

    @Bean
    public WebClient webClientForA() {
        return webClientFactory.create(
                "baseurlA",
                Duration.ofMillis(1_000),
                Duration.ofMillis(5_000)
        );
    }

    @Bean
    public WebClient webClientForB() {
        return webClientFactory.create(
                "baseurlB",
                Duration.ofMillis(2_000),
                Duration.ofMillis(10_000)
        );
    }
}

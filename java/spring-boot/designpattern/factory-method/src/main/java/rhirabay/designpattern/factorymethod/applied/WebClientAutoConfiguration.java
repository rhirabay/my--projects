package rhirabay.designpattern.factorymethod.applied;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class WebClientAutoConfiguration {
    private final WebClientFactory webClientFactory;

    @Bean
    public WebClient webClientForA() {
        return webClientFactory.create("a");
    }

    @Bean
    public WebClient webClientForB() {
        return webClientFactory.create("b");
    }
}

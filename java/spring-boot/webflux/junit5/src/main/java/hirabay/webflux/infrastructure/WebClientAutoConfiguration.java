package hirabay.webflux.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientAutoConfiguration {
    // SpringBootが起動するようにダミーのWebClientを定義
    @Bean
    public WebClient webClient() {
        return WebClient.create();
    }
}

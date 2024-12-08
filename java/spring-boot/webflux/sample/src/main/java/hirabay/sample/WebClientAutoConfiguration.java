package hirabay.sample;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientAutoConfiguration {
    //    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return null;
    }
}

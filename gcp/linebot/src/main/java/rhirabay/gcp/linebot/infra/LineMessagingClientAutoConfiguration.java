package rhirabay.gcp.linebot.infra;

import com.linecorp.bot.client.LineMessagingClient;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(LineMessagingClientProperties.class)
public class LineMessagingClientAutoConfiguration {
    private final LineMessagingClientProperties properties;

    @Bean
    public LineMessagingClient tdlLineMessagingClient() {
        return LineMessagingClient.builder(properties.getToken("tdl"))
                .build();
    }
}

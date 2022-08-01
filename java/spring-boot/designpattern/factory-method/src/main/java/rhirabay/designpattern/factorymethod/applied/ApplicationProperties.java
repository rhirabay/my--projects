package rhirabay.designpattern.factorymethod.applied;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.Map;

@Data
@ConfigurationProperties(prefix = "my-pj")
public class ApplicationProperties {
    private Map<String, WebClientProperties> webClient;

    @Data
    public static class WebClientProperties {
        private String baseurl;
        private Duration readTimeout;
        private Duration connectTimeout;
    }
}

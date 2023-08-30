package hirabay.bootstrap.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("my-app")
public class MyApplicationProperties {
    private String key;
}

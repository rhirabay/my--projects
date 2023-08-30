package hirabay.bootstrap.bootstrap;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("boostrap.sample")
public class SampleProperties {
    private String key;
}

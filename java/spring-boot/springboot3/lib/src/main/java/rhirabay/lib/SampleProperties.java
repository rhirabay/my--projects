package rhirabay.lib;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("custom-props.sample-api")
public class SampleProperties {
    private String rootUri;
    private String username;
    private String password;
}

package hirabay.beans;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Data
@ConfigurationProperties("sample-app")
public class RestTemplateProperties {
    private Map<String, RestTemplateProperty> restTemplates;

    @Data
    public static class RestTemplateProperty {
        private String rootUri;
    }
}

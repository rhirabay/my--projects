package rhirabay.lib;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Slf4j
//@AutoConfiguration
@Configuration
@EnableConfigurationProperties(SampleProperties.class)
public class SampleAutoConfiguration {
    @Bean
    public SampleComponent sampleComponent() {
        return new SampleComponent();
    }

    @Bean
    public RestTemplate restTemplate(SampleProperties properties) {
        return new RestTemplateBuilder()
                .basicAuthentication(properties.getUsername(), properties.getPassword())
                .rootUri(properties.getRootUri())
                .build();
    }
}

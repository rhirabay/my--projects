package hirabay.migration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class SampleAutoConfiguration {
    @Bean
    public SampleClient sampleClient() {
        return new SampleClient();
    }
}

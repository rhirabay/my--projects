package rhirabay.lib;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
//@AutoConfiguration
@Configuration
public class SampleAutoConfiguration {
    @Bean
    public SampleComponent sampleComponent() {
        return new SampleComponent();
    }
}

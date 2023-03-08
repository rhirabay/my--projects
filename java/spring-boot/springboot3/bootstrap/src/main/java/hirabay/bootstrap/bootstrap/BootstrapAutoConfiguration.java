package hirabay.bootstrap.bootstrap;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.BootstrapRegistry;
import org.springframework.boot.BootstrapRegistryInitializer;
import org.springframework.boot.context.config.StandardConfigDataLoader;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Slf4j
@EnableConfigurationProperties(SampleProperties.class)
@RequiredArgsConstructor
public class BootstrapAutoConfiguration {
    private final SampleProperties sampleProperties;

    @PostConstruct
    void postConstruct() {
        log.info("constructed");
        log.info("sampleProperties: {}", sampleProperties);
    }
}

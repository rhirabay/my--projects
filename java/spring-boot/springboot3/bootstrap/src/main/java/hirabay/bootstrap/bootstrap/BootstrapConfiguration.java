package hirabay.bootstrap.bootstrap;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.BootstrapContext;
import org.springframework.boot.BootstrapRegistry;
import org.springframework.boot.BootstrapRegistryInitializer;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.DefaultBootstrapContext;
import org.springframework.boot.context.config.ConfigDataLoaderContext;
import org.springframework.boot.context.config.StandardConfigDataLoader;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Slf4j
@EnableConfigurationProperties(SampleProperties.class)
@RequiredArgsConstructor
public class BootstrapConfiguration implements BootstrapRegistryInitializer {
    private final SampleProperties sampleProperties;

    @Override
    public void initialize(BootstrapRegistry registry) {
        var configDataLoader = new StandardConfigDataLoader();

        System.setProperty("my-app.key", "value");

        log.info("sampleProperties: {}", sampleProperties);
//        log.info("myAppKey: {}", myAppKey);

//        registry.
    }
}

package rhirabay.newrelic;

import com.newrelic.telemetry.micrometer.NewRelicRegistry;
import com.newrelic.telemetry.micrometer.NewRelicRegistryConfig;
import io.micrometer.core.instrument.config.MeterFilter;
import io.micrometer.core.instrument.util.NamedThreadFactory;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.CompositeMeterRegistryAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.export.simple.SimpleMetricsExportAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@AutoConfigureBefore({
        CompositeMeterRegistryAutoConfiguration.class,
        SimpleMetricsExportAutoConfiguration.class
})
public class NewRelicMetricsConfiguration {
    @Bean
    @SneakyThrows
    public NewRelicRegistry newRelicMeterRegistry(NewRelicRegistryConfig config) {
        NewRelicRegistry newRelicRegistry =
                NewRelicRegistry.builder(config)
                        .build();
        newRelicRegistry.config().meterFilter(MeterFilter.acceptNameStartsWith("http"));
        newRelicRegistry.start(new NamedThreadFactory("newrelic.micrometer.registry"));
        return newRelicRegistry;
    }

    @Bean
    public NewRelicRegistryConfig newRelicConfig(
            @Value("${newrelic.api-key}") String apiKey
    ) {
        return new NewRelicRegistryConfig() {
            @Override
            public String get(String key) {
                return null;
            }

            @Override
            public String apiKey() {
                return apiKey;
            }

            @Override
            public Duration step() {
                return Duration.ofSeconds(5);
            }

            @Override
            public String serviceName() {
                return "newrelic-sample";
            }
        };
    }
}

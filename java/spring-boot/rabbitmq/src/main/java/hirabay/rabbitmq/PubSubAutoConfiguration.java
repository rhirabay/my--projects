package hirabay.rabbitmq;

import hirabay.rabbitmq.domain.SampleMessage;
import io.micrometer.tracing.Baggage;
import io.micrometer.tracing.brave.bridge.BraveTracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class PubSubAutoConfiguration {
    private final BraveTracer tracer;

    @Bean("sample1-receive")
    public Consumer<SampleMessage> sampleReceive() {
        return message -> {
            Baggage baggage = tracer.getBaggage("sample-baggage");
            log.info("Received: {}", message);
            log.info("baggage: {}", baggage.get());
        };
    }
}

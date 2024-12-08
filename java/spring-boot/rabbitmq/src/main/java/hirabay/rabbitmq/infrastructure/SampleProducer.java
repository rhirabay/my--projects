package hirabay.rabbitmq.infrastructure;

import hirabay.rabbitmq.domain.SampleMessage;
import io.micrometer.tracing.Baggage;
import io.micrometer.tracing.brave.bridge.BraveTracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SampleProducer {
    private final StreamBridge streamBridge;
    private final BraveTracer tracer;

    public void send(SampleMessage message) {
        Baggage baggage = tracer.getBaggage("sample-baggage");
        // baggage.set("test");

        var result = streamBridge.send("sample-send", message);
        if (result) {
            log.info("queued success.");
        } else {
            log.error("queued failed.");
        }
    }
}

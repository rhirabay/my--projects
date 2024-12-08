package hirabay.rabbitmq;

import hirabay.rabbitmq.domain.SampleMessage;
import hirabay.rabbitmq.infrastructure.SampleProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SampleController {
    private final SampleProducer sampleProducer;

    @GetMapping("/**")
    public String sample() {
        log.info("sample request received.");
        var sampleMessage = SampleMessage.builder().greeting("Hello").build();
        sampleProducer.send(sampleMessage);

        return "queued";
    }
}

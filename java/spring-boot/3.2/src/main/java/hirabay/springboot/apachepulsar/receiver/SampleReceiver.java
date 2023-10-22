package hirabay.springboot.apachepulsar.receiver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.pulsar.annotation.PulsarListener;
import org.springframework.stereotype.Component;

@Slf4j
//@Component
public class SampleReceiver {
    @PulsarListener(topics = "someTopic")
    public void processMessage(String content) {
        log.info("receipt message: {}", content);
    }
}

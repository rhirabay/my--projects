package hirabay.springboot.apachepulsar.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.pulsar.core.PulsarTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SampleController {
    private final PulsarTemplate<String> pulsarTemplate;

    @GetMapping("/**")
    public Object any() {
        return "Hello world.";
    }

    @GetMapping("/send")
    @SneakyThrows
    public Object sendMessage() {
        return this.pulsarTemplate.send("someTopic", "Hello");
    }
}

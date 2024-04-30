package hirabay.springboot;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SampleController {
    private final MeterRegistry meterRegistry;

    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }
}

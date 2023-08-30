package hirabay.springboot.retry;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SampleController {
    private final SampleClient sampleClient;

    @GetMapping("/**")
    public String sample() {
        try {
            return sampleClient.hello();
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }
}

package hirabay.migration.mockmvctester;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SampleController {
    @GetMapping("/mockMvcTester/sample")
    public Object sample() {
        return Map.of(
                "message", "Hello, World!",
                "detail", Map.of(
                        "key1", "value1",
                        "key2", "value2"
                )
        );
    }
}

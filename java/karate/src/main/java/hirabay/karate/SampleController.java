package hirabay.karate;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SampleController {
    @GetMapping("/sample")
    public Object sample(@RequestParam(defaultValue = "anonymous", required = false) String name) {
        return Map.of(
                "name", name
        );
    }
}

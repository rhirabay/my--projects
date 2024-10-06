package hirabay.bootstrap;

import hirabay.bootstrap.properties.MyApplicationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableConfigurationProperties(MyApplicationProperties.class)
@RequiredArgsConstructor
public class SampleController {
    private final MyApplicationProperties myApplicationProperties;

    @GetMapping("/props")
    public String get() {
        return myApplicationProperties.getKey();
    }
}

package hirabay.springboot3;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/restTemplate")
public class RestTemplateSampleController {
    private final RestTemplate restTemplate = new RestTemplateBuilder()
            .rootUri("http://localhost:8080/restTemplate")
            .build();

    @GetMapping("/client")
    public String client() {
        var uri = URI.create("/server?name=りょう");
        log.info("uri: {}", uri);

        return restTemplate.getForObject(uri, String.class);
    }

    @GetMapping("/server")
    public String server(@RequestParam(required = false) String name) {
        return name;
    }
}

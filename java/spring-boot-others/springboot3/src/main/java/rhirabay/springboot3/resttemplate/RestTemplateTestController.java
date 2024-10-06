package rhirabay.springboot3.resttemplate;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
public class RestTemplateTestController {
    private final RestTemplate restTemplate1;

    @GetMapping("/rest-template/client")
    public String client() {
        return restTemplate1.getForObject("/rest-template/server", String.class);
    }

    @GetMapping("/rest-template/server")
    public String server() {
        return "ok";
    }
}

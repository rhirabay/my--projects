package hirbaay.webmvc.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/rest-template-test")
@RequiredArgsConstructor
public class RestTemplateTestController {
    private final RestTemplate restTemplate;
    private final RestTemplate restTemplateWithMetrics;

    @GetMapping("/client")
    public Object client() {
        return restTemplate.getForObject("/server", String.class);
    }

    @GetMapping("/server/**")
    @SneakyThrows
    public Object server() {
//        TimeUnit.SECONDS.sleep(5);
        return "hello";
    }

    @GetMapping("/client/metrics")
    public Object clientMetrics() {
        var uuid = UUID.randomUUID().toString();
        return restTemplateWithMetrics.getForObject("/server?uuid={uuid}", String.class, uuid);
    }

}

package hirabay.beans;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@RestController
public class SampleController {
    private final RestTemplate restTemplate1;
    private final RestTemplate restTemplate2;

    @GetMapping("/ok")
    public String ok() {
        return "ok";
    }

    @GetMapping("/test1")
    public String test1() {
        return restTemplate1.getForObject("/ok", String.class);
    }

    @GetMapping("/test2")
    public String test2() {
        return restTemplate2.getForObject("/ok", String.class);
    }
}

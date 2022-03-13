package rhirabay.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import rhirabay.model.User;
import rhirabay.repository.UserRepository;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SampleController {
    private final RestTemplate restTemplate;
    private final UserRepository userRepository;

    @GetMapping("/proxy/hello/{id}")
    public String proxyToHello(@PathVariable String id) {
        return restTemplate.getForObject("/hello/{id}", String.class, id);
    }

    @GetMapping("/hello/{id}")
    public String hello(@PathVariable String id) {
        var user = userRepository.findById(id);
        return "Hello, " + user.map(User::getName).orElse("jaeger") + ".";
    }
}

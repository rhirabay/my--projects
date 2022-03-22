package rhirabay.grpc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rhirabay.grpc.client.SampleClient;

@RestController
@RequiredArgsConstructor
public class SampleController {
    private final SampleClient sampleClient;

    @GetMapping("/greeting")
    public String greeting(@RequestParam(defaultValue = "anonymous", required = false) String name) {
        return sampleClient.greeting(name);
    }
}

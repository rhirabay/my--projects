package rhirabay.grpc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rhirabay.grpc.client.SampleClient;
import rhirabay.grpc.client.TlsSampleClient;

@RestController
@RequiredArgsConstructor
public class SampleController {
    private final SampleClient sampleClient;
    private final TlsSampleClient tlsSampleClient;

    @GetMapping("/greeting")
    public String greeting(
            @RequestParam(defaultValue = "anonymous", required = false) String name) {
        return sampleClient.greeting(name);
    }

    @GetMapping("/tls-greeting")
    public String tlsGreeting(
            @RequestParam(defaultValue = "anonymous", required = false) String name) {
        return tlsSampleClient.greeting(name);
    }
}

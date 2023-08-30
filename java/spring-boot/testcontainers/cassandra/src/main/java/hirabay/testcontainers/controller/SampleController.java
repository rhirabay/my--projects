package hirabay.testcontainers.controller;

import hirabay.testcontainers.infrastructure.CassandraClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SampleController {
    private final CassandraClient cassandraClient;

    @GetMapping("/findAll")
    public Object sample() {
        return cassandraClient.findAll();
    }
}

package hirabay.testcontainers.controller;

import hirabay.testcontainers.infrastructure.RedisClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SampleController {
    private final RedisClient redisClient;

    @GetMapping("/get/{key}")
    public String get(@PathVariable String key) {
        return redisClient.get(key);
    }

    @GetMapping("/set/{key}/{value}")
    public void set(@PathVariable String key, @PathVariable String value) {
        redisClient.set(key, value);
    }
}

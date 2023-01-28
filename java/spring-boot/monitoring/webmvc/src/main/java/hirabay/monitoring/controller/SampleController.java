package hirabay.monitoring.controller;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@RestController
public class SampleController {
    private final RestTemplate myRestTemplate;

    @GetMapping("/sample")
    @SneakyThrows
    public String sample() {
        TimeUnit.SECONDS.sleep(1);
        return "ok";
    }

    @GetMapping("/server")
    public String server() {
        return "ok";
    }

    @GetMapping("/client")
    public String client() {
        return myRestTemplate.getForObject("http://localhost:8080/server", String.class);
    }

    @Timed(
            // メトリクス名
            value = "timed.metrics",
            // パーセンタイルを計測したい場合に指定（例では50%ileと99%ileを指定）
            percentiles = {0.5, 0.99}
    )
    @GetMapping("/timed")
    public String timed() {
        return "timed";
    }

    @Counted(
            // メトリクス名
            value = "counted.metrics"
    )
    @GetMapping("/counted")
    public String counted() {
        return "counted";
    }
}

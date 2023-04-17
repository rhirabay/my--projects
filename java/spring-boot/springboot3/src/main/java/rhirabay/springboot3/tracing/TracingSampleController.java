package rhirabay.springboot3.tracing;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.metrics.web.client.ObservationRestTemplateCustomizer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
public class TracingSampleController {
    private RestTemplate restTemplate;

    public TracingSampleController(
            RestTemplateBuilder builder,
            ObservationRestTemplateCustomizer observationRestTemplateCustomizer
    ) {
//        this.restTemplate = builder.build();
        this.restTemplate = new RestTemplateBuilder()
                .additionalCustomizers(observationRestTemplateCustomizer)
                .build();
    }

    @GetMapping("/tracing")
    public String tracing() {
        log.info("tracing test.");
        return "ok";
    }

    @GetMapping("/tracing/client")
    public String client() {
        log.info("client");
        return restTemplate.getForObject("http://localhost:8080/tracing/server", String.class);
    }

    @GetMapping("/tracing/server")
    public String server() {
        log.info("server");
        return "server";
    }
}

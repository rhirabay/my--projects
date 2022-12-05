package rhirabay.springboot3.metrics.prometheus;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PrometheusTestController {
    @GetMapping("/metrics/test/{id}")
    public String test(@PathVariable String id) {
        return "id = " + id;
    }
}

package rhirabay.springboot3.tracing;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TracingSampleController {
    @GetMapping("/tracing")
    public String tracing() {
        log.info("tracing test.");
        return "ok";
    }
}

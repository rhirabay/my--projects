package rhirabay.springboot3.log;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LogSample {
    @PostConstruct
    void test() {
        log.info("test");
    }
}

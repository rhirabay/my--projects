package hirabay.bootstrap.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class SystemPropertyBootstrapAutoConfiguration {
    public SystemPropertyBootstrapAutoConfiguration() {
        System.setProperty("sample.password", "12345678");
        log.info("set sample.password");
    }
}

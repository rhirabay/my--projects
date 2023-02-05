package hirabay.sample;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@Controller
@Slf4j
public class SampleController {
    @GetMapping("/sample")
    public String sample(
            @CookieValue(value = "key", required = false) String cookie,
            @RequestHeader(value = "header-key", required = false) String header,
            @RequestHeader(value = "user-agent", required = false) String userAgent
    ) {
        log.info("cookie: {}, header: {}, userAgent: {}", cookie, header, userAgent);
        return "sample";
    }
}

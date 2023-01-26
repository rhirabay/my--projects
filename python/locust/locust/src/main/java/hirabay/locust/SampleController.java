package hirabay.locust;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class SampleController {
    @RequestMapping("/sample")
    public String sample(
            @RequestBody(required = false) String body,
            @RequestParam(required = false) String key
    ) {
        log.info("body: {}, key: {}", body, key);
        return "ok";
    }

    @RequestMapping("/ng")
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String ng() {
        return "ng";
    }
}

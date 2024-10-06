package rhirabay.springboot3.urlmatching;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {
    @GetMapping("/url-matching/test")
    public String test() {
        return "ok";
    }
}

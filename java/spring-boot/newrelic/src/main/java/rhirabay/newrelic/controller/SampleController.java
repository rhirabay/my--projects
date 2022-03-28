package rhirabay.newrelic.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {
    @GetMapping("/hello")
    public String hello(@RequestParam(defaultValue = "anonymous", required = false) String name) {
        return "Hello, " + name + ".";
    }
}

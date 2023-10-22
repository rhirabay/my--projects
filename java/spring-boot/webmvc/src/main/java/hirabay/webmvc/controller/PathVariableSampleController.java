package hirabay.webmvc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/pathVariableSample")
public class PathVariableSampleController {
    @GetMapping("/**{path}")
    public Object multiPath(@PathVariable String path) {
        return path;
    }
}

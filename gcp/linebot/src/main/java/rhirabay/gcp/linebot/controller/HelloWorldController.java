package rhirabay.gcp.linebot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class HelloWorldController {
    @RequestMapping("/**")
    public String root(ServerHttpRequest request) {
        log.warn("receive request to undefined path. {}", request.getURI().getPath());
        return "This is application for line bot.";
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello world.";
    }
}
